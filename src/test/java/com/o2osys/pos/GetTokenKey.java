package com.o2osys.pos;

import org.apache.commons.codec.binary.Base64;

public class GetTokenKey {

	public static void main(String args[]) {
		
		String tokenAuthInfoByte = org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.encodeBase64("MANNA01:MANNA171103".getBytes()));
		
		System.out.println(tokenAuthInfoByte);
		
//		{
//		    "err_cd": 0,
//		    "err_msg": "",
//		    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNQU5OQTAxIiwidXNlcl9ubyI6IjgiLCJkYiI6IkZBMyIsImV4cCI6MTUyNzE1NTI4OH0.aQXxO7qCu8Tv3gUwteIrB-ObF6gxd_uxHl6HcG0KyJwoKxWwS7s0O9AYd06iyQigmOFD6UKQL6QGT-J4dxM18w"
//		}
		
//		{"err_cd":9000, "err_msg":"unknown error"}

	}
}
