<?php
    if (isset($this->htmlOptions['class'])) {
        $this->htmlOptions['class'] .= ' progress-bar';
    } else {
        $this->htmlOptions['class'] = 'progress-bar';
    }
    echo CHtml::tag('div', $this->htmlOptions, false, false);
    $stepsCount = count($this->steps);
    foreach($this->steps as $i => $step) {
        $cssClass = 'progress-bar-step';
        if ($i < $this->currentStep) {
            $cssClass .= ' completed';
        } else if ($i === $this->currentStep) {
            $cssClass .= ' current';
        }
        $width = number_format(100 / $stepsCount, 2);
        echo CHtml::tag('span', array('class' => $cssClass, 'style' => 'width: ' . $width . '%'), $step);
    }
    echo CHtml::closeTag('div');
?>