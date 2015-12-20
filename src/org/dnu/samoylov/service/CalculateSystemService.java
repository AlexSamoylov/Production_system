package org.dnu.samoylov.service;

import org.dnu.samoylov.event.*;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.ClarifyingRule;
import org.dnu.samoylov.model.rule.PsResult;
import org.dnu.samoylov.model.rule.ResultingRule;
import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.storage.SelectedLabelStorage;
import org.dnu.samoylov.storage.input.RuleStorage;

import java.util.*;
import java.util.stream.Collectors;

public class CalculateSystemService {
    private static final int MAX_STEP_COUNT = 100;
    final List<PsResult> results = new LinkedList<>();
    final JavaFxBus javaFxBus = JavaFxBus.getInstance();


    public boolean isQuickCalculate = true;
    private boolean mayGoNextStep;

    public void runCalculateByStep() {
        isQuickCalculate = false;
        new Thread(() -> {
            try {
                process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void runQuickCalculate() {
        isQuickCalculate = true;
        new Thread(() -> {
            try {
                process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void goNextStep() {
        mayGoNextStep = true;
    }

    private void process() throws InterruptedException {
        final List<PsLabel> currentLabelList = SelectedLabelStorage.getInstance().getList();
        final List<Rule> newSuitableRule = new LinkedList<>();
        List<Rule> ruleList =
                new ArrayList<>(RuleStorage.getInstance().getList());
        final List<PsLabel> newLabels = new LinkedList<>();

        boolean isExistProcessedRule;
        int counter = 0;
        while (counter++ < MAX_STEP_COUNT) {
            if (!isQuickCalculate) {
                while (!mayGoNextStep) {
                    Thread.sleep(100);
                }
                mayGoNextStep = false;
            }

            javaFxBus.post(LogEvent.create("шаг " + counter));
            isExistProcessedRule = false;
            for (final Rule rule : ruleList) {
                final boolean ruleSuitable = isRuleSuitable(currentLabelList, rule);
                if (ruleSuitable) {
                    final List<PsLabel> newL = handleSuitableRule(rule);
                    newSuitableRule.add(rule);
                    newLabels.addAll(newL);
                    isExistProcessedRule = true;
                }
            }

            if (isExistProcessedRule) {
                ruleList = commitNewKnowledge(currentLabelList, ruleList, newSuitableRule, newLabels);
            } else {
                processResult();
                finishCalculateMethod();
                return;
            }
        }
    }



    private List<Rule> commitNewKnowledge(List<PsLabel> currentLabelList, List<Rule> ruleList, List<Rule> newSuitableRule, List<PsLabel> newLabels) {
        currentLabelList.addAll(newLabels);
        newLabels.clear();
        List<Rule> filteredRule = ruleList.stream()
                .filter(rule -> !newSuitableRule.contains(rule))
                .collect(Collectors.toList());
        ruleList.clear();
        newSuitableRule.clear();
        ruleList = filteredRule;
        return ruleList;
    }

    private void finishCalculateMethod() {

    }

    private void processResult() {
        if (results.size()==0) {
            javaFxBus.post(LogEvent.create("нет подходящего результата"));
            return;
        }

        if (results.size()==1) {
            javaFxBus.post(SelectFinalResultEvent.create(results.get(0)));
            return;
        }

        javaFxBus.post(LogEvent.create("поиск наиприоритетнейшего результата:"));
        javaFxBus.post(LogEvent.create("фильтрация по заданному приоритету"));

        final List<PsResult> firstFilterResult = results.stream()
                .collect(Collectors.groupingBy(PsResult::getPriority))
                .entrySet().stream().max((o1, o2) -> o1.getKey() - o2.getKey()).get().getValue();


        firstFilterResult.stream().forEach(result ->
                        javaFxBus.post(
                                LogEvent.create("\t" + result.toString()))
        );

        if (firstFilterResult.size()==1) {
            javaFxBus.post(SelectFinalResultEvent.create(results.get(0)));
            return;
        }

        javaFxBus.post(LogEvent.create("фильтрация по количеству входящих данных"));
        final List<PsResult> secondFilterResult = firstFilterResult.stream()
                .collect(Collectors.groupingBy(PsResult::getSubPriority))
                .entrySet().stream().max((o1, o2) -> o1.getKey() - o2.getKey()).get().getValue();

        secondFilterResult.stream().forEach(result ->
                        javaFxBus.post(
                                LogEvent.create("\t" + result.toString()))
        );

        if (secondFilterResult.size()==1) {
            javaFxBus.post(SelectFinalResultEvent.create(results.get(0)));
            return;
        }

        javaFxBus.post(LogEvent.create("фильтрация новизне"));

        final PsResult mostPriorityResult = secondFilterResult.stream()
                .collect(Collectors.maxBy((o11, o21) -> o11.getId() - o21.getId())).get();

        javaFxBus.post(
                LogEvent.create("\t" + mostPriorityResult.toString()));

        javaFxBus.post(SelectFinalResultEvent.create(mostPriorityResult));

    }

    private List<PsLabel> handleSuitableRule(Rule rule) {
        List<PsLabel> newLabels = Collections.emptyList();
        javaFxBus.post(FindSuitableRuleEvent.create(rule));

        if (rule instanceof ClarifyingRule) {
            final List<PsLabel> outLabels = ((ClarifyingRule) rule).getOut();
            javaFxBus.post(AddingNewLabelsEvent.create(outLabels));
            newLabels = outLabels;
        } else if (rule instanceof ResultingRule) {
            final PsResult result = ((ResultingRule) rule).getResult();
            results.add(result);
            javaFxBus.post(AddingNewResultEvents.create(result));
        }

        return newLabels;
    }

    private boolean isRuleSuitable(List<PsLabel> currentLabelList, Rule rule) {
        List<PsLabel> inputLabels = new ArrayList<>(rule.getInput());
        boolean isRuleSuitable = false;
        for (PsLabel existingLabel : currentLabelList) {

            List<PsLabel> removeLabels = inputLabels.stream().
                    filter(neededLabels -> neededLabels.equals(existingLabel)).
                    collect(Collectors.toCollection(LinkedList::new));

            removeLabels.forEach(inputLabels::remove);

            if (inputLabels.size()==0) {
                break;
            }
        }
        if (inputLabels.size()==0) {
            isRuleSuitable = true;
        }

        return isRuleSuitable;
    }
}
