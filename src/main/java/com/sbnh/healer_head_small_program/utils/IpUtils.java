package com.sbnh.healer_head_small_program.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: weis
 * @create: 2021-08-06-17:22
 * @note:
 */
public class IpUtils {
  public static String getIp(HttpServletRequest request) {
    String unknown = "unknown";
    if (request == null) {
      return unknown;
    }

    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }

    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }

    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }
}
