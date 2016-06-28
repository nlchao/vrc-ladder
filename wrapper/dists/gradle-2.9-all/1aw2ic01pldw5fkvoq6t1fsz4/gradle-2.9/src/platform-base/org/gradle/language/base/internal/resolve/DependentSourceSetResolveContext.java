/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.language.base.internal.resolve;

import org.gradle.api.artifacts.component.LibraryBinaryIdentifier;
import org.gradle.api.internal.artifacts.ResolveContext;
import org.gradle.api.internal.artifacts.configurations.ResolutionStrategyInternal;
import org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy.DefaultResolutionStrategy;
import org.gradle.internal.component.local.model.DefaultLibraryBinaryIdentifier;
import org.gradle.internal.component.model.ComponentResolveMetaData;
import org.gradle.language.base.internal.DependentSourceSetInternal;
import org.gradle.language.base.internal.model.DefaultLibraryLocalComponentMetaData;
import org.gradle.language.base.internal.model.VariantsMetaData;
import org.gradle.platform.base.DependencySpec;

import java.util.Iterator;

public class DependentSourceSetResolveContext implements ResolveContext {
    private final LibraryBinaryIdentifier binaryId;
    private final DependentSourceSetInternal sourceSet;
    private final ResolutionStrategyInternal resolutionStrategy = new DefaultResolutionStrategy();
    private final VariantsMetaData variants;
    private final Iterable<DependencySpec> dependencies;

    public DependentSourceSetResolveContext(LibraryBinaryIdentifier binaryId, final DependentSourceSetInternal sourceSet, VariantsMetaData variants) {
        this(binaryId, sourceSet, variants, dependenciesOf(sourceSet));
    }

    public DependentSourceSetResolveContext(LibraryBinaryIdentifier binaryId, DependentSourceSetInternal sourceSet, VariantsMetaData variants, Iterable<DependencySpec> dependencies) {
        this.binaryId = binaryId;
        this.sourceSet = sourceSet;
        this.variants = variants;
        this.dependencies = dependencies;
    }

    @Override
    public String getName() {
        return DefaultLibraryBinaryIdentifier.CONFIGURATION_API;
    }

    @Override
    public String getDisplayName() {
        return sourceSet.getDisplayName();
    }

    public LibraryBinaryIdentifier getComponentId() {
        return binaryId;
    }

    public DependentSourceSetInternal getSourceSet() {
        return sourceSet;
    }

    public VariantsMetaData getVariants() {
        return variants;
    }

    @Override
    public ResolutionStrategyInternal getResolutionStrategy() {
        return resolutionStrategy;
    }

    @Override
    public ComponentResolveMetaData toRootComponentMetaData() {
        LibraryBinaryIdentifier libraryBinaryIdentifier = getComponentId();
        DefaultLibraryLocalComponentMetaData metaData = DefaultLibraryLocalComponentMetaData.newMetaData(libraryBinaryIdentifier, sourceSet.getBuildDependencies());
        addDependenciesTo(metaData, libraryBinaryIdentifier.getProjectPath(), dependencies);
        return metaData;
    }

    private void addDependenciesTo(DefaultLibraryLocalComponentMetaData metaData, String defaultProject, Iterable<DependencySpec> dependencies) {
        for (DependencySpec dependency : dependencies) {
            metaData.addDependency(dependency, defaultProject);
        }
    }

    private static Iterable<DependencySpec> dependenciesOf(final DependentSourceSetInternal sourceSet) {
        return new Iterable<DependencySpec>() {
            @Override
            public Iterator<DependencySpec> iterator() {
                return sourceSet.getDependencies().getDependencies().iterator();
            }
        };
    }

}
