package com.Quokka.service;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Quokka.Jobhunter.domain.Company;
import com.Quokka.Jobhunter.domain.Role;
import com.Quokka.Jobhunter.domain.User;
import com.Quokka.Jobhunter.domain.res.ResultPaginationResponse;
import com.Quokka.Jobhunter.domain.res.user.CreatedUserResponse;
import com.Quokka.Jobhunter.domain.res.user.UpdatedUserResponse;
import com.Quokka.Jobhunter.repository.UserRepository;
import com.Quokka.Jobhunter.util.convert.UserConvert;
import com.Quokka.Jobhunter.util.error.IdInvalidException;
import com.Quokka.Jobhunter.util.response.FormatResultPagaination;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public CreatedUserResponse createUser(User user) throws Exception {
        String email = user.getEmail();

        if(user.getCompany() != null){
            Company company = this.companyService.findCompanyById(user.getCompany().getId());
            user.setCompany(company);
        }

        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if(user.getRole() != null){
            Role role = this.roleService.fetchRoleById(user.getRole().getId());
            user.setRole(role);
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return UserConvert.convertToResCreatedUserRes(this.userRepository.save(user));
    }

    public CreatedUserResponse fetchUserById(Long id) throws Exception {
        if(userRepository.existsById(id)){
            return UserConvert.convertToResCreatedUserRes(this.userRepository.findById(id).get());
        }else{
            throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public void deleteUser(Long id) throws Exception {
        if(this.userRepository.existsById(id)){
            this.userRepository.deleteById(id);
        }else{
         throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public ResultPaginationResponse getAllUser(Pageable pageable, Specification<User> spec){
        Page<User> userPage = this.userRepository.findAll(spec, pageable);

        return FormatResultPagaination.createPaginateUserRes(userPage);
    }

    public UpdatedUserResponse updateUser(Long id, UpdateUserDTO user) throws Exception {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            User currentUser = userOptional.get();
            currentUser.setName(user.getName());
            currentUser.setGender(user.getGender());
            currentUser.setAge(user.getAge());
            currentUser.setAddress(user.getAddress());

            if(user.getCompany() != null){
                Company company = this.companyService.findCompanyById(user.getCompany().getId());
                if(company == null){
                    throw new IdInvalidException("Company Id is invalid");
                }
                currentUser.setCompany(company);
            }

            if(user.getRole() != null){
                Role role = this.roleService.fetchRoleById(user.getRole().getId());
                user.setRole(role);
            }

            return UserConvert.convertToResUpdatedUserRes(this.userRepository.save(currentUser));
        }
        return null;
    }

    public void updateUserToken(String token, String email){
        User user = this.handleGetUserByUsername(email);
        if(user != null){
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email){
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }
}
