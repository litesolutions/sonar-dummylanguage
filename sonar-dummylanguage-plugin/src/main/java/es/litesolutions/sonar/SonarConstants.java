package es.litesolutions.sonar;

/**
 * A class of constants used by SonarQube
 */
public final class SonarConstants
{
    /**
     * The default quality profile: "Sonar Way"
     */
    public static final String DEFAULT_QUALITY_PROFILE = "Sonar way";

    /**
     * The default rule repository: "SonarQube"
     */
    public static final String DEFAULT_RULE_REPOSITORY = "SonarQube";

    private SonarConstants()
    {
        throw new Error("nice try!");
    }
}
