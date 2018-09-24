package com.qred.task.domain.controller;


import com.qred.task.domain.dto.LoanApplicationDto;
import com.qred.task.domain.exception.LoanNotValidatedException;
import com.qred.task.domain.exception.LoanPaymentLimitExceededException;
import com.qred.task.domain.responseDto.*;
import com.qred.task.domain.exception.BlackListedException;
import com.qred.task.domain.exception.LoanApplyParameterInputException;
import com.qred.task.domain.service.LoanService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/v1")
public class LoanController {


    @Inject
    private LoanService loanService;

    @RequestMapping(value = "/applyForLoan",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity applyForLoan(@RequestHeader HttpHeaders headers,
                                       @RequestBody LoanApplicationDto loanRequest) {

        try {
            LoanApplyResponseDto loanApplyResponseDto = loanService.applyForLoan(loanRequest);

            if (loanApplyResponseDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(loanApplyResponseDto);
            }

        } catch (BlackListedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseErrorDto(ex.toString()));
        } catch (LoanApplyParameterInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto(ex.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }
    }


    @RequestMapping(value = "/listLoanApplications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listLoanApplicationsByCompany(@RequestParam(value = "companyRegistrationNumber", required = false) String companyRegistrationNumber) {

        try {
            LoanApplicationsByCompanyDto company = null;
            LoansByCompanies loanApplications = null;

            if (companyRegistrationNumber != null) {
                company = loanService.getLoanApplicationsByCompany(companyRegistrationNumber);
            } else {
                loanApplications = loanService.getAllLoanApplications();
            }


            if (company != null) {
                return ResponseEntity.status(HttpStatus.OK).body(company);
            }
            if (loanApplications != null) {
                return ResponseEntity.status(HttpStatus.OK).body(loanApplications);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }

    }


    @RequestMapping(value = "/changeLoanApplicationStatus",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeLoanApplicationStatus(@RequestParam("companyRegistrationNumber") String companyRegistrationNumber,
                                                      @RequestParam("loanId") String loanId,
                                                      @RequestParam(value = "isRejected", required = false) String loanStatusFromRequest) {

        try {

            boolean loanStatus = true;
            if (loanStatusFromRequest != null) {
                loanStatus = Boolean.parseBoolean(loanStatusFromRequest);
            }

            ResponseDto loanApplyResponseDto = loanService.rejectLoanApplication(companyRegistrationNumber, Integer.parseInt(loanId), loanStatus);

            if (loanApplyResponseDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(loanApplyResponseDto);
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }

    }


    @RequestMapping(value = "/validateLoanApplication",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateLoanApplication(@RequestParam("companyRegistrationNumber") String companyRegistrationNumber,
                                                  @RequestParam("loanId") String loanId) {

        try {
            ResponseDto loanApplyResponseDto = loanService.validateLoanApplication(companyRegistrationNumber, Integer.parseInt(loanId));

            if (loanApplyResponseDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(loanApplyResponseDto);
            }

        } catch (LoanPaymentLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto(e.toString()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }
    }


    @RequestMapping(value = "/confirmLoanApplication",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity confirmLoanApplication(@RequestParam("companyRegistrationNumber") String companyRegistrationNumber,
                                                 @RequestParam("loanId") String loanId) {

        try {
            LoanConfirmResponseDto loanConfirmResponseDto = loanService.confirmLoanApplication(companyRegistrationNumber, Integer.parseInt(loanId));

            if (loanConfirmResponseDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(loanConfirmResponseDto);
            }

        } catch (LoanNotValidatedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseErrorDto(e.toString()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }
    }


    @RequestMapping(value = "/showLoanScheduler",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity showLoanScheduler(@RequestParam("companyRegistrationNumber") String companyRegistrationNumber,
                                            @RequestParam("loanId") String loanId) {

        try {

            LoanSchedurelResponseDto loanConfirmResponseDto = loanService.getLoanSchedule(companyRegistrationNumber, Integer.parseInt(loanId));
            if (loanConfirmResponseDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(loanConfirmResponseDto);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.toString()));
        }

    }


}

























