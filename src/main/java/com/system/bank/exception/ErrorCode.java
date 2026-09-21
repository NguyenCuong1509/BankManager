package com.system.bank.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định.", HttpStatus.INTERNAL_SERVER_ERROR),
    RESULT_INVALID(1001, "Dữ liệu không hợp lệ.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "Không tìm thấy khách hàng.", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(1003, "Không tìm thấy tài khoản ngân hàng.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_BALANCE(1004, "Số dư không đủ để thực hiện giao dịch.", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1005, "Tên đăng nhập đã tồn tại.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Tên đăng nhập hoặc mật khẩu không chính xác.", HttpStatus.UNAUTHORIZED),
    USER_LOCKED(1007, "Tài khoản người dùng đã bị khóa.", HttpStatus.FORBIDDEN),
    ACCOUNT_LOCKED(1008, "Tài khoản ngân hàng đã bị khóa, không thể giao dịch.", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT(1009, "Số tiền giao dịch phải lớn hơn 0.", HttpStatus.BAD_REQUEST),
    DAILY_LIMIT_EXCEEDED(1010, "Giao dịch vượt quá hạn mức tối đa trong ngày.", HttpStatus.BAD_REQUEST),
    SAME_ACCOUNT_TRANSFER(1011, "Tài khoản nhận không được trùng với tài khoản chuyển.", HttpStatus.BAD_REQUEST),
    TRANSACTION_NOT_FOUND(1012, "Không tìm thấy thông tin giao dịch.", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
