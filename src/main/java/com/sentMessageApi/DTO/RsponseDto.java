package com.sentMessageApi.DTO;
import java.util.List;

import lombok.Data;



@Data
public class RsponseDto {
 private String msg;
 private  List <blacklistDto>  blacklist;
 private int status;


}
