package com.dinfo.fi.dto;

import com.dinfo.fi.enums.GrabStatusType;
import com.dinfo.fi.enums.ShareType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;
import org.apache.commons.net.ntp.TimeStamp;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GrabStatusDto {
    private String grabTime;
    private GrabStatusType grabStatusType;
    private ShareType shareType;
}
