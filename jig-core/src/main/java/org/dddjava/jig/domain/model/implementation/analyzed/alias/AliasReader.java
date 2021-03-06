package org.dddjava.jig.domain.model.implementation.analyzed.alias;

import org.dddjava.jig.domain.model.implementation.raw.JavaSources;
import org.dddjava.jig.domain.model.implementation.raw.PackageInfoSources;

/**
 * 別名読み取り機
 */
public interface AliasReader {

    PackageNames readPackages(PackageInfoSources packageInfoSources);

    TypeNames readTypes(JavaSources javaSources);
}
