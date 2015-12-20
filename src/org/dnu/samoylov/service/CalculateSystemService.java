package org.dnu.samoylov.service;

import org.dnu.samoylov.event.AddingNewLabelsEvent;
import org.dnu.samoylov.event.AddingNewResultEvents;
import org.dnu.samoylov.event.FindSuitableRuleEvent;
import org.dnu.samoylov.event.SelectFinalResultEvent;
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

    public void process() {
        final List<PsLabel> currentLabelList = SelectedLabelStorage.getInstance().getList();
        final List<Rule> newSuitableRule = new LinkedList<>();
        List<Rule> ruleList =
                new ArrayList<>(RuleStorage.getInstance().getList());
        final List<PsLabel> newLabels = new LinkedList<>();

        boolean isExistProcessedRule;
        int counter = 0;
        while (counter++ < MAX_STEP_COUNT) {
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
            return;
        }

        if (results.size()==1) {
            javaFxBus.post(SelectFinalResultEvent.create(results.get(0)));
            return;
        }

        final PsResult mostPriorityResult = results.stream()
                .collect(Collectors.groupingBy(PsResult::getPriority))
                .entrySet().stream().max((o1, o2) -> o1.getKey() - o2.getKey()).get().getValue()

                .stream()
                .collect(Collectors.groupingBy(PsResult::getSubPriority))
                .entrySet().stream().max((o1, o2) -> o1.getKey() - o2.getKey()).get().getValue()

                .stream()
                .collect(Collectors.maxBy((o11, o21) -> o11.getId() - o21.getId())).get();

        javaFxBus.post(SelectFinalResultEvent.create(mostPriorityResult));

    }

    private List<PsLabel> handleSuitableRule(Rule rule) {
        List<PsLabel> newLabels = Collections.emptyList();

        if (rule instanceof ClarifyingRule) {
            final List<PsLabel> outLabels = ((ClarifyingRule) rule).getOut();
            javaFxBus.post(AddingNewLabelsEvent.create(outLabels));
            newLabels = outLabels;
        } else if (rule instanceof ResultingRule) {
            final PsResult result = ((ResultingRule) rule).getResult();
            results.add(result);
            javaFxBus.post(AddingNewResultEvents.create(result));
        }
        javaFxBus.post(FindSuitableRuleEvent.create(rule));
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
