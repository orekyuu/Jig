package org.dddjava.jig.domain.model.implementation.analyzed;

import org.dddjava.jig.domain.model.implementation.analyzed.bytecode.TypeByteCodes;
import org.dddjava.jig.domain.model.implementation.analyzed.datasource.Sqls;
import org.dddjava.jig.domain.model.implementation.raw.RawSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析した実装
 */
public class AnalyzedImplementation {

    RawSource rawSource;
    TypeByteCodes typeByteCodes;
    Sqls sqls;

    public AnalyzedImplementation(RawSource rawSource, TypeByteCodes typeByteCodes, Sqls sqls) {
        this.rawSource = rawSource;
        this.typeByteCodes = typeByteCodes;
        this.sqls = sqls;
    }

    public TypeByteCodes typeByteCodes() {
        return typeByteCodes;
    }

    public Sqls sqls() {
        return sqls;
    }

    public AnalyzeStatuses status() {
        List<AnalyzeStatus> list = new ArrayList<>();

        if (rawSource.nothingBinarySource()) {
            list.add(AnalyzeStatus.バイナリソースなし);
        }

        if (rawSource.nothingTextSource()) {
            list.add(AnalyzeStatus.テキストソースなし);
        }

        // binarySourceがあってtypeByteCodesがない（ASMの解析で失敗する）のは現状実行時エラーになるのでここでは考慮しない

        if (sqls.status().not正常()) {
            list.add(AnalyzeStatus.fromSqlReadStatus(sqls.status()));
        }

        return new AnalyzeStatuses(list);
    }
}
