package jig.domain.model.relation.dependency;

import jig.domain.model.identifier.PackageIdentifier;

import java.util.Objects;

public class PackageDependency {
    PackageIdentifier from;
    PackageIdentifier to;

    public PackageDependency(PackageIdentifier from, PackageIdentifier to) {
        this.from = from;
        this.to = to;
    }

    public PackageIdentifier from() {
        return from;
    }

    public PackageIdentifier to() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageDependency packageDependency = (PackageDependency) o;
        return Objects.equals(from, packageDependency.from) &&
                Objects.equals(to, packageDependency.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public PackageDependency applyDepth(Depth depth) {
        return new PackageDependency(from.applyDepth(depth), to.applyDepth(depth));
    }

    public boolean notSelfRelation() {
        return !from.equals(to);
    }
}