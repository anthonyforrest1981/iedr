<?php

class CommandResultUtility {

    static public function renderResult($controller, $model) {
        Yii::log('RENDER RESULTS');
        $summaryFunction = null;
        $postSummaryFunction = null;
        switch ($model->command) {
            case 'payonline':
                $headers = array('Domains<br>by payment online','Transaction ID','Registration Date','Renewal Date',
                    'Payment Period','Fee','VAT','Total');
                $value = $model->commandresults['payonline']->paymentDomains;
                $rowFunction = 'paymentRow';
                $summaryFunction = 'paymentSummary';
                break;
            case 'paydeposit':
                $headers = array('Domains<br>by payment from deposit','Transaction ID','Registration Date',
                    'Renewal Date','Payment Period','Fee','VAT','Total');
                $value = $model->commandresults['paydeposit']->paymentDomains;
                $rowFunction = 'paymentRow';
                $summaryFunction = 'paymentSummary';
                break;
            case 'voluntary':
            case 'removefromvoluntary':
                $headers = array('Domain','Registration Date','Renewal Date','Status');
                $value = $model->commandresults[$model->command];
                $rowFunction = 'domainRow';
                break;
            case 'autorenew':
            case 'noautorenew':
                $headers = array('Domain','Registration Date','Renewal Date','Renewal Mode');
                $value = $model->commandresults[$model->command];
                $rowFunction = 'domainRow';
                break;
            case 'authcodedownload':
                $headers = array('Domain','Result');
                $value = $model->commandresults[$model->command];
                $rowFunction = 'authcodeRow';
                $postSummaryFunction = "exportAuthcodeData";
                break;
        }
        $result = $model->commandresults['result'];
        echo '<table class="gridtable">';
        self::renderHeaders($headers);
        if (is_object($value)) {
            self::$rowFunction($model, $value, $result);
        } else if (is_array($value)) {
            for ($i = 0; $i < count($value); $i++) {
                self::$rowFunction($model, $value[$i], $result[$i]);
            }
        }
        if (isset($summaryFunction)) {
            self::$summaryFunction($model, $value);
        }
        echo '</table>';
        if (isset($postSummaryFunction)) {
            self::$postSummaryFunction($controller, $model);
        }
    }

    static private function renderHeaders($headers) {
        echo '<tr>';
        foreach ($headers as $header) {
            echo '<td class="gridtablecell"><strong>' . $header . '</strong></td>';
        }
        echo '</tr>';
    }

    static private function paymentRow($model, $value, $result) {
        echo '<tr><td class="gridtablecell">' . $value->domainName . '</td>';
        echo '<td class="gridtablecell"> ' . $model->commandresults[$model->command]->orderId . ' </td>';
        echo '<td class="gridtablecell"> ' . parseXmlDate($value->registrationDate) . ' </td>';
        echo '<td class="gridtablecell"> ' . parseXmlDate($value->renewalDate) . ' </td>';
        echo '<td class="gridtablecell"> ' . $value->periodInYears . ' </td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->fee) . '</td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->vat) . '</td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($value->total)) . '</td></tr>';
    }

    static private function domainRow($model, $value, $result) {
        echo "<tr><td class='gridtablecell'>" . $value->name . "</td>";
        echo "<td class='gridtablecell'>" . parseXmlDate($value->registrationDate) . "</td>";
        echo "<td class='gridtablecell'>" . parseXmlDate($value->renewalDate) . "</td>";
        echo "<td class='gridtablecell'>" . $result . "</td></tr>";
    }

    static private function authcodeRow($model, $value, $result) {
        echo "<tr><td class='gridtablecell'>" . $value . "</td>";
        echo "<td class='gridtablecell'>" . $result . "</td></tr>";
    }

    static private function paymentSummary($model, $value) {
        $fee = 0.00;
        $vat = 0.00;
        $totals = 0.00;
        if (is_object($value)) {
            $fee = $value->fee;
            $vat = $value->vat;
            $totals = $value->total;
        } else if (is_array($value)) {
            for ($i = 0; $i < count($value); $i++) {
                $fee += $value[$i]->fee;
                $vat += $value[$i]->vat;
                $totals += $value[$i]->total;
            }
        }
        echo '<tr><td class="gridtablecell"><strong>Totals</strong></td>';
        echo '<td class="gridtablecell" colspan="4"></td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($fee) . '</td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($vat) . '</td>';
        echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($totals)) . '</td></tr>';
    }

    static private function exportAuthcodeData($controller, $model) {
        $form = $controller->beginWidget('CActiveForm', array('id' => 'CSVAction'));
        echo $form->hiddenField($model, 'domainlist');
        for ($i = 0; $i < count($model->commandresults['exportData']); $i++) {
            echo $form->hiddenField($model, "commandresults[exportData]" . "[$i]" . "[A]");
            echo $form->hiddenField($model, "commandresults[exportData]" . "[$i]" . "[B]");
            echo $form->hiddenField($model, "commandresults[exportData]" . "[$i]" . "[C]");
        }
        echo '<div class="row buttons">' . CHtml::submitButton('Download CSV file', array('name' => 'CSVFile')) . '</div><br>';
        $controller->endWidget();
    }

}