package com.inspur.pmv6.udf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.List;

/**
 * Usage:
 * Created by Chen on 26/03/2018.
 */
public class UDF_Union extends UDF {

    static final Log LOG = LogFactory.getLog(UDF_Union.class.getName());

    public Double evaluate(List<Double> values) {

        LOG.info("start udf union calculate");

        Double result = 0.0;

        if (values != null && values.size() > 0) {
            result = values.stream().reduce((v1, v2) -> ((v1 != 0) || (v2 != 0)) ? 1.0 : 0.0).orElseGet(() -> 0.0);
        }

        LOG.info("udf union result : " + result);

        return result;
    }

}
