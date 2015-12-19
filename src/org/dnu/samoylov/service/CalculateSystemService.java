package org.dnu.samoylov.service;

import org.dnu.samoylov.event.AddingNewLabelsEvent;
import org.dnu.samoylov.event.AddingNewResultEvents;
import org.dnu.samoylov.event.FindSuitableRuleEvent;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.ClarifyingRule;
import org.dnu.samoylov.model.rule.PsResult;
import org.dnu.samoylov.model.rule.ResultingRule;
import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.storage.input.RuleStorage;

import java.util.*;
import java.util.stream.Collectors;

public class CalculateSystemService {
    final List<PsResult> results = new LinkedList<>();
    final JavaFxBus javaFxBus = JavaFxBus.getInstance();

    public void process() {
        final List<PsLabel> currentLabelList = FullLabelStorage.getInstance().getList();
        final List<PsLabel> newLabels = new LinkedList<>();

        boolean isExistProcessedRule = false;

        for (final Rule rule : RuleStorage.getInstance().getList()) {
            final boolean ruleSuitable = isRuleSuitable(currentLabelList, rule);
            if (ruleSuitable) {
                final List<PsLabel> newL = handleSuitableRule(rule);
                newLabels.addAll(newL);
                isExistProcessedRule = true;
            }
        }

        if (isExistProcessedRule) {
            currentLabelList.addAll(newLabels);
        } else {
            processResult();
            finishCalculateMethod();
        }

    }

    private void finishCalculateMethod() {

    }

    private void processResult() {

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
            javaFxBus.post(AddingNewResultEvents.create(results));
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
                isRuleSuitable = true;
                break;
            }
        }

        return isRuleSuitable;
    }
}
