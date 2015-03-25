/*
 * SonarQube Python Plugin
 * Copyright (C) 2011 SonarSource and Waleri Enns
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package es.litesolutions.sonar.dummylanguage;

import es.litesolutions.sonar.SonarConstants;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Injection of rules definitions into a rule repository
 *
 * <p>You need checks to be present in a repository in order for them to be
 * accessible by quality profiles. Therefore all your checks which you use in
 * either profile definition needs to be registered in at least one repository.
 * </p>
 *
 * <p>This implementation stores all the checks in the default rule repository.
 * </p>
 *
 * @see SonarConstants#DEFAULT_RULE_REPOSITORY
 */
@ParametersAreNonnullByDefault
public class DummyLanguageRulesDefinition
    implements RulesDefinition
{
    @Override
    public void define(final Context context)
    {
        final String languageKey = DummyLanguageLanguage.KEY;
        final String repositoryKey = DummyLanguageChecks.REPOSITORY_KEY;

        @SuppressWarnings("rawtypes")
        final List<Class> list = DummyLanguageChecks.all();

        final NewRepository repository
            = context.createRepository(repositoryKey, languageKey)
            .setName(SonarConstants.DEFAULT_RULE_REPOSITORY);

        AnnotationBasedRulesDefinition.load(repository, languageKey, list);

        repository.done();
    }
}
