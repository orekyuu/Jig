package org.dddjava.jig.domain.model.implementation.analyzed.declaration.namespace;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * パッケージ識別子一覧
 */
public class PackageIdentifiers {

    List<PackageIdentifier> list;

    public PackageIdentifiers(List<PackageIdentifier> list) {
        this.list = list;
    }

    public Stream<PackageIdentifier> stream() {
        return list.stream();
    }

    public PackageTree tree() {
        return PackageTree.of(list);
    }

    public PackageIdentifiers applyDepth(PackageDepth packageDepth) {
        List<PackageIdentifier> list = this.list.stream()
                .map(identifier -> identifier.applyDepth(packageDepth))
                .distinct()
                .collect(toList());
        return new PackageIdentifiers(list);
    }

    public AllPackageIdentifiers allPackageIdentifiers() {
        return new AllPackageIdentifiers(list);
    }

    public PackageDepth maxDepth() {
        return list.stream()
                .map(PackageIdentifier::depth)
                .max(Comparator.comparing(PackageDepth::value))
                .orElseGet(() -> new PackageDepth(0));
    }

    public boolean contains(PackageIdentifier packageIdentifier) {
        return list.contains(packageIdentifier);
    }

    public PackageNumber number() {
        return new PackageNumber(list.size());
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public List<PackageIdentifier> list() {
        return list;
    }
}
