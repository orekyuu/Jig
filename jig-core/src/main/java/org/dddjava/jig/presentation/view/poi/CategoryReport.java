package org.dddjava.jig.presentation.view.poi;

import org.dddjava.jig.domain.basic.UserNumber;
import org.dddjava.jig.domain.model.categories.CategoryAngle;
import org.dddjava.jig.domain.model.characteristic.Characteristic;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifierFormatter;
import org.dddjava.jig.domain.model.japanese.JapaneseName;
import org.dddjava.jig.presentation.view.poi.report.ConvertibleItem;
import org.dddjava.jig.presentation.view.poi.report.Report;

import java.util.List;
import java.util.function.Function;

/**
 * 区分レポート
 */
public class CategoryReport {

    /**
     * レポート項目
     */
    private enum Items implements ConvertibleItem<Row> {
        クラス名(Row::クラス名),
        クラス和名(Row::クラス和名),
        定数宣言(Row::定数宣言),
        フィールド(Row::フィールド),
        使用箇所数(Row::使用箇所数),
        使用箇所(Row::使用箇所),
        パラメーター有り(Row::パラメーター有り),
        振る舞い有り(Row::振る舞い有り),
        多態(Row::多態);

        Function<Row, String> func;

        Items(Function<Row, String> func) {
            this.func = func;
        }

        @Override
        public String convert(Row row) {
            return func.apply(row);
        }
    }

    private final List<Row> list;

    public CategoryReport(List<Row> list) {
        this.list = list;
    }

    public Report<?> toReport() {
        return new Report<>("ENUM", list, Items.values());
    }

    public static class Row {
        CategoryAngle categoryAngle;
        JapaneseName japaneseName;
        TypeIdentifierFormatter identifierFormatter;

        public Row(CategoryAngle categoryAngle, JapaneseName japaneseName, TypeIdentifierFormatter identifierFormatter) {
            this.categoryAngle = categoryAngle;
            this.japaneseName = japaneseName;
            this.identifierFormatter = identifierFormatter;
        }

        String クラス名() {
            return categoryAngle.typeIdentifier().format(identifierFormatter);
        }

        String クラス和名() {
            return japaneseName.summarySentence();
        }

        String 定数宣言() {
            return categoryAngle.constantsDeclarations().toNameText();
        }

        String フィールド() {
            return categoryAngle.fieldDeclarations().toSignatureText();
        }

        String 使用箇所() {
            return categoryAngle.userTypeIdentifiers().asSimpleText();
        }

        String 使用箇所数() {
            return new UserNumber(categoryAngle.userTypeIdentifiers().list().size()).asText();
        }

        String パラメーター有り() {
            return categoryAngle.characteristics().has(Characteristic.ENUM_PARAMETERIZED) ? "◯" : "";
        }

        String 振る舞い有り() {
            return categoryAngle.characteristics().has(Characteristic.ENUM_BEHAVIOUR) ? "◯" : "";
        }

        String 多態() {
            return categoryAngle.characteristics().has(Characteristic.ENUM_POLYMORPHISM) ? "◯" : "";
        }
    }
}