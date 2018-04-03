package com.inspur.pmv6.udf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;

import java.util.List;

/**
 * Usage:
 * Created by Chen on 26/03/2018.
 */
public class UDF_Multi extends AbstractGenericUDAFResolver {

    static final Log LOG = LogFactory.getLog(UDF_Multi.class.getName());

    public Double evaluate(List<Double> values) {

        LOG.info("start udf multi calculate");

        Double result = 0.0;

        if (values != null && values.size() > 0) {
            result = values.stream().reduce((v1, v2) -> v1 * v2).orElseGet(() -> 0.0);
        }

        LOG.info("udf multi result : " + result);

        return result;
    }
}
