package com.raphael.jwttwst.utils;

import com.raphael.jwttwst.exception.PaginationException;

public class PageUtil {

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw  new PaginationException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw  new PaginationException("Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw  new PaginationException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
