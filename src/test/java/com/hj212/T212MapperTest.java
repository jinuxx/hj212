package com.hj212;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj212.format.hbt212.core.T212Mapper;
import com.hj212.format.hbt212.core.feature.VerifyFeature;
import com.hj212.format.hbt212.exception.T212FormatException;
import com.hj212.format.hbt212.model.CpData;
import com.hj212.format.hbt212.model.Data;
import com.hj212.format.hbt212.model.DataFlag;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

public class T212MapperTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void readStringData() throws T212FormatException, IOException {
        String msg = null;
        msg = "##0453QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;LA-td=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&c0c1\\r\\n";
        // msg = "##0285QN=20190925181031464;ST=22;CN=2061;PW=BF470F88957588DE902D1A52;MN=Z13401000010301;Flag=5;CP=&&DataTime=20190924220000;a34006-Avg=2.69700,a34006-Flag=N;a34007-Avg=7.96600,a34007-Flag=N;a34048-Avg=3.30600,a34048-Flag=N;a34047-Avg=7.35700,a34047-Flag=N;a34049-Avg=10.66300,a34049-Flag=N&&C181\\r\\n";
        // msg = "##0101QN=20160801085857223;ST=32;CN=1062;PW=100000;MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1C80\\r\\n";
        T212Mapper t212Mapper = new T212Mapper().enable(VerifyFeature.DATA_CRC);
        Data data = t212Mapper.readData(msg);
        System.out.println(objectMapper.writeValueAsString(data));
    }


    @Test
    public void answer() throws T212FormatException, IOException {
        String msg = null;
        msg = "##0453QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;LA-td=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&c0c1\r\n";
        // msg = "##0285QN=20190925181031464;ST=22;CN=2061;PW=BF470F88957588DE902D1A52;MN=Z13401000010301;Flag=5;CP=&&DataTime=20190924220000;a34006-Avg=2.69700,a34006-Flag=N;a34007-Avg=7.96600,a34007-Flag=N;a34048-Avg=3.30600,a34048-Flag=N;a34047-Avg=7.35700,a34047-Flag=N;a34049-Avg=10.66300,a34049-Flag=N&&C181\\r\\n";
        // msg = "##0101QN=20160801085857223;ST=32;CN=1062;PW=100000;MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1C80\\r\\n";
        T212Mapper t212Mapper = new T212Mapper().enable(VerifyFeature.DATA_CRC);
        Data data = t212Mapper.readData(msg);

        Data answer = new Data();
        answer.setQn(data.getQn());
        answer.setSt("91"); // 系统交互
        answer.setCn("9014");  // 数据应答命令
        answer.setPw(data.getPw());
        answer.setMn(data.getMn());
        answer.setDataFlag(Collections.singletonList(DataFlag.V0));
        answer.setCp(new CpData());

        String asString = t212Mapper.writeDataAsString(answer);
        System.out.println(asString);

        // ##0087QN=20160801085000001;ST=91;CN=9014;PW=123456;MN=010000A8900016F000169DC0;Flag=4;CP=&&&&C241

    }
}