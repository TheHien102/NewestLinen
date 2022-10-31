package com.example.newestlinen.jwt;


import com.example.newestlinen.utils.ZipUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class UserJwt implements Serializable {

    public static final String DELIM = "::";
    public static final String EMPTY_STRING = "<>";
    private Long accountId = -1L;
    private Long orgId = -1L;
    private String username = EMPTY_STRING;
    private String pemission = EMPTY_STRING;
    private String deviceId = EMPTY_STRING;
    private Integer userKind = -1; //loại user là admin hay là gì
    private String kind = EMPTY_STRING;//token kind
    private Boolean isSuperAdmin = false;

    public String toClaim(){
        return ZipUtils.zipString(accountId+DELIM+ orgId +DELIM+kind+DELIM+pemission+DELIM+deviceId+DELIM+userKind+DELIM+username+DELIM+isSuperAdmin) ;
    }

    public static UserJwt decode(String input){
        UserJwt result = null;
        try {
            String[] items = ZipUtils.unzipString(input).split(DELIM);
            if(items.length == 8){
                result = new UserJwt();
                result.setAccountId(parserLong(items[0]));
                result.setOrgId(parserLong(items[1]));
                result.setKind(checkString(items[2]));
                result.setPemission(checkString(items[3]));
                result.setDeviceId(checkString(items[4]));
                result.setUserKind(parserInt(items[5]));
                result.setUsername(checkString(items[6]));
                result.setIsSuperAdmin(checkBoolean(items[7]));
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return  result;
    }

    private static Long parserLong(String input){
        try{
            Long out = Long.parseLong(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static Integer parserInt(String input){
        try{
            Integer out = Integer.parseInt(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static String checkString(String input){
        if(!input.equals(EMPTY_STRING)){
            return  input;
        }
        return  null;
    }

    private static Boolean checkBoolean(String input){
        try{
            return Boolean.parseBoolean(input);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public String toString() {
        return "UserJwt{" +
                "accountId=" + accountId +
                ", orgId=" + orgId +
                ", username='" + username + '\'' +
                ", pemission='" + pemission + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userKind=" + userKind +
                ", kind='" + kind + '\'' +
                '}';
    }
}
