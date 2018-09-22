package cn.zectec.contraceptive.management.system.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author duanpeizhou on 2018/9/22 下午6:24.
 */

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = this.escapeHtml(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        //return StringEscapeUtils.escapeHtml(value);
        return this.escapeHtml(value);
    }

    /**
     * 重写StringEscapeUtils.escapeHtml()方法，避免过滤中文
     *
     * @param s
     * @return
     */
    private String escapeHtml(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append('>');
                    break;
                case '<':
                    sb.append('<');
                    break;
                case '"':
                    sb.append('"');
                    break;
                case '&':
                    sb.append('&');
                    break;
                case 10:
                case 13:
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }
}
