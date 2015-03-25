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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class DummyLanguageRulesDefinition
    implements RulesDefinition
{
    private static final String REPOSITORY_NAME = "SonarQube";

    @Override
    public void define(final Context context)
    {
        final String languageKey = DummyLanguageLanguage.KEY;
        final String repositoryKey = DummyLanguageChecks.REPOSITORY_KEY;

        @SuppressWarnings("rawtypes")
        final List<Class> list = DummyLanguageChecks.all();

        final NewRepository repository
            = context.createRepository(repositoryKey, languageKey)
            .setName(REPOSITORY_NAME);

        AnnotationBasedRulesDefinition.load(repository, languageKey, list);

        repository.done();
    }
}
