/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.icoalert.api.data

class ErrorEntity {

    var errorCode: Int = 0
    var errorDesc = ""

    val isError: Boolean
        get() = errorCode > 0

    constructor() {}

    constructor(errorCode: Int) {
        this.errorCode = errorCode
    }

    companion object {
        val ERROR_CODE_TIMEOUT = 0x1001
        val ERROR_NO_INTERNET = 0x1002
        val ERROR_CODE_NO_CONTENT = 0x1003
        val ERROR_UNKNOUN_EXEPTION = 0x1004
        val ERROR_CODE_NOT_FOUND = 0x1005
        val ERROR_CODE_BAD_REQUEST = 0x1006
        val ERROR_CODE_SERVER_EXCEPTION = 0x1007
        val ERROR_CODE_METHOD_NOT_ALLOWED = 0x1008
        val ERROR_AUTH_NEEDED = 1
    }
}