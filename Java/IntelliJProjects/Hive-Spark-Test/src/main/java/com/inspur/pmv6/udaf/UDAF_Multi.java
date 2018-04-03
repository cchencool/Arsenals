package com.inspur.pmv6.udaf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Usage:
 * Created by Chen on 26/03/2018.
 */
public class UDAF_Multi extends AbstractGenericUDAFResolver {

    static final Log LOG = LogFactory.getLog(UDAF_Multi.class.getName());

    @Override
    public GenericUDAFEvaluator getEvaluator(GenericUDAFParameterInfo info) throws SemanticException {

        TypeInfo[] parameters = info.getParameters();
        if (parameters.length != 1) {
            throw new UDFArgumentTypeException(parameters.length - 1,
                    "Please specify exactly one arguments.");
        }

        // validate the first parameter, which is the expression to compute over
//        if (parameters[0].getCategory() != ObjectInspector.Category.STRUCT) {
//            throw new UDFArgumentTypeException(0,
//                    "Only primitive type arguments are accepted but "
//                            + parameters[0].getTypeName() + " was passed as parameter 1.");
//        }

        switch (((PrimitiveTypeInfo) parameters[0]).getPrimitiveCategory()) {
            case DOUBLE:
                break;
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case STRING:
            case BOOLEAN:
            default:
                throw new UDFArgumentTypeException(0,
                        "Only numeric type arguments are accepted but "
                                + parameters[0].getTypeName() + " was passed as parameter.");
        }

        return new Multi_Evaluator();
    }


    public static class Multi_Evaluator extends GenericUDAFEvaluator {

        // For PARTIAL1 and COMPLETE: ObjectInspectors for original data
        private PrimitiveObjectInspector P1OI;

        // For PARTIAL2 and FINAL: ObjectInspectors for partial aggregations (list of doubles)
        private Double result;

        public class AggBuffer extends AbstractAggregationBuffer {

            public Double resultDouble = null;

            public List<Double> values = null;

        }

        /**
         * Initialize the evaluator.
         *
         * @param m
         *          The mode of aggregation.
         * @param parameters
         *          The ObjectInspector for the parameters: In PARTIAL1 and COMPLETE
         *          mode, the parameters are original data; In PARTIAL2 and FINAL
         *          mode, the parameters are just partial aggregations (in that case,
         *          the array will always have a single element).
         * @return The ObjectInspector for the return value. In PARTIAL1 and PARTIAL2
         *         mode, the ObjectInspector for the return value of
         *         terminatePartial() call; In FINAL and COMPLETE mode, the
         *         ObjectInspector for the return value of terminate() call.
         *
         *         NOTE: We need ObjectInspector[] (in addition to the TypeInfo[] in
         *         GenericUDAFResolver) for 2 reasons: 1. ObjectInspector contains
         *         more information than TypeInfo; and GenericUDAFEvaluator.init at
         *         execution time. 2. We call GenericUDAFResolver.getEvaluator at
         *         compilation time,
         */
        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
            // This function should be overriden in every sub class
            // And the sub class should call super.init(m, parameters) to get mode set.

            LOG.info("[" + this.toString() + "] enter init.");

            super.init(m, parameters);

            P1OI = (PrimitiveObjectInspector) parameters[0];
//            return ObjectInspectorFactory.getStandardListObjectInspector((PrimitiveObjectInspector) ObjectInspectorUtils.getStandardObjectInspector(P1OI));

            return PrimitiveObjectInspectorFactory.javaDoubleObjectInspector;
        }



        /**
         * Get a new aggregation object.
         */
        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {

            LOG.info("[" + this.toString() + "] enter getNewAggregationBuffer.");

            AggregationBuffer aggBuffer = new AggBuffer();

            reset(aggBuffer);

            return aggBuffer;
        }

        /**
         * Reset the aggregation. This is useful if we want to reuse the same
         * aggregation.
         *
         * @param agg
         */
        @Override
        public void reset(AggregationBuffer agg) throws HiveException {

            LOG.info("[" + this.toString() + "] enter reset.");

            if (agg != null) {
                ((AggBuffer) agg).resultDouble = 1.0;
                ((AggBuffer) agg).values = new ArrayList<Double>();
            }
        }

        /**
         * Iterate through original data.
         *
         * @param agg
         * @param parameters
         */
        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {

            if (null != parameters && parameters.length > 0) {

//                Double result = Arrays.stream(parameters).map(v -> (Double) v).reduce((v1, v2) -> v1 * v2).orElseGet(() -> 1.0);

//                ((AggBuffer) agg).resultDouble *= (Double) parameters[0];

                LOG.info("[" + this.toString() + "] enter iterate. add value:" + parameters[0]);

                ((AggBuffer) agg).values.add(PrimitiveObjectInspectorUtils.getDouble(parameters[0], P1OI));

//                merge(agg, parameters[0]);
            }

        }

        /**
         * Get partial aggregation result.
         *
         * @param agg
         * @return partial aggregation result.
         */
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {

            LOG.info("[" + this.toString() + "] enter terminatePartial.");

            ((AggBuffer) agg).resultDouble = ((AggBuffer) agg).values.stream().reduce((a, b) -> a * b).orElseGet(() -> 1.0);

            return ((AggBuffer) agg).resultDouble;
        }

        /**
         * Merge with partial aggregation result. NOTE: null might be passed in case
         * there is no input data.
         *
         * @param agg
         * @param partial
         */
        @Override
        public void merge(AggregationBuffer agg, Object partial) throws HiveException {

            LOG.info("[" + this.toString() + "] enter merge.");

            if (partial != null) {
                ((AggBuffer) agg).resultDouble *= PrimitiveObjectInspectorUtils.getDouble(partial, P1OI);
            }

        }

        /**
         * Get final aggregation result.
         *
         * @param agg
         * @return final aggregation result.
         */
        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {

            LOG.info("[" + this.toString() + "] enter terminate.");

            return ((AggBuffer) agg).resultDouble;
        }

        @Override
        public String toString() {
            return "MultiEval" + Integer.toHexString(hashCode());
        }
    }
}
