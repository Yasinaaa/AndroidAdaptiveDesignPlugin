package ru.itis.androidplugin.settings.interfaces;

import com.intellij.psi.*;
import com.intellij.psi.util.PropertyUtil;

import java.util.Collection;

/**
 * Created by yasina on 24.02.17.
 */
public class LayoutPattern extends InsertMetodToClass {
    @Override
    public String getName() {
        return "ViewHolder pattern";
    }

    @Override
    public String getSuggestedClassName(String layoutFileName) {
        return super.getSuggestedClassName(layoutFileName) + "Holder";
    }

    private void generateGetters(PsiClass psiClass, Collection<PsiField> psiFields) {
        for (PsiField psiField : psiFields) {
            PsiMethod method = PropertyUtil.generateGetterPrototype(psiField);
            if (method != null) {
                psiClass.add(method);
            }
        }
    }

}
