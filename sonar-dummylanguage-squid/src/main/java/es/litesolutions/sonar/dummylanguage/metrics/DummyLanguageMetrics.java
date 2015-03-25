package es.litesolutions.sonar.dummylanguage.metrics;

import org.sonar.squidbridge.measures.CalculatedMetricFormula;
import org.sonar.squidbridge.measures.MetricDef;

public enum DummyLanguageMetrics
    implements MetricDef
{
    FILES,
    ;

    @Override
    public String getName()
    {
        return name();
    }

    @Override
    public boolean isCalculatedMetric()
    {
        return false;
    }

    @Override
    public boolean aggregateIfThereIsAlreadyAValue()
    {
        return true;
    }

    @Override
    public boolean isThereAggregationFormula()
    {
        return false;
    }

    @Override
    public CalculatedMetricFormula getCalculatedMetricFormula()
    {
        return null;
    }
}
