# Delete created VAT rate (rate is kept as imprecise float)
DELETE FROM Vat WHERE Category = 'A' AND From_Dt = CURDATE() AND ABS(Rate - 0.05) < 0.01;
