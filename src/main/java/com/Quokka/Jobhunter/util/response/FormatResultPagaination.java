package com.Quokka.Jobhunter.util.response;

import org.springframework.data.domain.Page;
import com.Quokka.Jobhunter.domain.User;
import com.Quokka.Jobhunter.domain.res.MetaResponse;
import com.Quokka.Jobhunter.domain.res.ResultPaginationResponse;
import com.Quokka.Jobhunter.domain.res.resume.FetchResumeResponse;
import com.Quokka.Jobhunter.domain.res.user.CreatedUserResponse;
import com.Quokka.Jobhunter.domain.Resume;
import com.Quokka.Jobhunter.util.convert.UserConvert;
import com.Quokka.Jobhunter.util.convert.ResumeConvert;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatResultPagaination {
    public static ResultPaginationResponse createPaginationResponse(Page page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);
        rs.setResult(page.getContent());

        return rs;
    }

    public static ResultPaginationResponse createPaginateUserRes(Page<User> page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<CreatedUserResponse> listUser = page.getContent()
                .stream().map(UserConvert::convertToResCreatedUserRes)
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

    public static ResultPaginationResponse createPaginateResumeRes(Page<Resume> page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<FetchResumeResponse> listResume = page.getContent()
                .stream().map(
                        ResumeConvert::convertToResFetchResumeRes
                ).toList();

        rs.setResult(listResume);

        return rs;
    }
}
