package ru.itis.androidplugin.generator.classes.helper;

import com.intellij.psi.PsiField;
import ru.itis.androidplugin.android.AndroidView;

import java.util.Map;

public class FindViewByIdStatementGenerator {

    public interface FindViewByIdCallback {
        void onStatementCreated(String statement, AndroidView view);
        String getViewGroupNameFor(AndroidView view);
        boolean shouldProcessView(AndroidView view);
    }

    public static abstract class ClassFieldAssigner implements FindViewByIdCallback {
        private Map<AndroidView, PsiField> fieldMappings;
        private String defaultViewGroup;

        public ClassFieldAssigner(Map<AndroidView, PsiField> fieldMappings, String defaultViewGroup) {
            this.fieldMappings = fieldMappings;
            this.defaultViewGroup = defaultViewGroup;
        }

        @Override
        public void onStatementCreated(String statement, AndroidView view) {
            PsiField psiField = fieldMappings.get(view);
            String resultStatement = psiField.getName() + " = " + statement;
            onStatementCreated(resultStatement, psiField, view);
        }

        protected abstract void onStatementCreated(String statement, PsiField field, AndroidView view);

        @Override
        public String getViewGroupNameFor(AndroidView view) {
            if (view.getParent() == null) {
                return defaultViewGroup;
            } else {
                PsiField field = fieldMappings.get(view.getParent());
                if (field == null) {
                    return defaultViewGroup;
                }
                return field.getName();
            }
        }

        @Override
        public boolean shouldProcessView(AndroidView view) {
            return true;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void createFindViewStatements(String type, AndroidView view, FindViewByIdCallback callback) {
        if (callback.shouldProcessView(view)) {
            String viewGroupName = callback.getViewGroupNameFor(view);
            String statement = createFindViewStatement(type, viewGroupName, view);
            callback.onStatementCreated(statement, view);
        }
        for (AndroidView subView : view.getChildNodes()) {
            createFindViewStatements(type,subView, callback);
        }
    }

    public String createFindViewStatement(String type, String viewGroupName, AndroidView view) {
        String answer = null;
        switch (type){
            case "Activity":
                answer = "(" + view.getClassSimpleName() + ") "
                        +"findViewById(R.id." + view.getIdValue() + ");";
                break;
            default:
                answer = ("view".equalsIgnoreCase(view.getClassSimpleName()) ? "" : "(" + view.getClassSimpleName() + ") ")
                        + viewGroupName + ".findViewById(R.id." + view.getIdValue() + ");";
                break;
        }
        return answer;
    }

}
