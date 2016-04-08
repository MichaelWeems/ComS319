lexer grammar lab9;

Email_xml:'<email>'Email'</email>'{System.out.println("Accepted: "+getText());};
Date_xml:'<date>'Date'</date>'{System.out.println("Accepted: "+getText());};
Card_xml:'<creditcard>'Card'</creditcard>'{System.out.println("Accepted: "+getText());};
Phone_xml:'<phone>'Phone'</phone>'{System.out.println("Accepted: "+getText());};

fragment X: 'X'|'x';
fragment M: 'M'|'m';
fragment L: 'L'|'l';

fragment Not_XML: [a-wyzA-WYZ]|[xX]([a-ln-zA-LN-Z]|[mM][a-km-zA-KM-Z]);
fragment Name: ('_'|Not_XML)(Letter|Digit|[-_.])*;
Reg_xml: '<'Name'>'.*?'</'Name'>'{System.out.println("Accepted: "+getText());};

fragment Letter: [a-zA-Z];
fragment Digit: [0-9];
fragment AlphaNum: (Letter | Digit);
fragment Char: [-_~$!&'()*+,;=:];

fragment Local: (AlphaNum | Char)+;
fragment Domain: (AlphaNum | '-')+('.'AlphaNum+)+;
fragment Email: (Local '@' Domain);

fragment Day: ('0'[1-9]|[1-2][0-9]|'3'[0-1]);
fragment Month: ('0'[1-9]|'1'[0-2]);
fragment Year: '2'('0' Digit Digit|'100');
fragment Date: Day '/' Month '/' Year;

fragment Digit_Two: Digit Digit;
fragment Digit_Three: Digit_Two Digit;
fragment Digit_Four: Digit_Three Digit;

fragment One: Digit_Three'-'Digit_Three'-'Digit_Four;
fragment Two: '(' Digit_Three ') ' Digit_Three'-'Digit_Four;
fragment Three: Digit_Three ' ' Digit_Three ' ' Digit_Four;
fragment Four: Digit_Three'.'Digit_Three'.'Digit_Four;
fragment Phone: (One|Two|Three|Four);

fragment Eleven: Digit_Four Digit_Four Digit_Three;
fragment Twelve: Eleven Digit;
fragment Thirteen: Twelve Digit;
fragment Fourteen: Thirteen Digit;
fragment Fifteen: Fourteen Digit;

fragment Visa_Old: Digit_Two'-'Digit_Three'-'Digit_Three'-'Digit_Four; 
fragment Visa_New: Digit_Three'-'Digit_Four'-'Digit_Four'-'Digit_Four;
fragment Visa:'4'(Visa_Old|Visa_New);
fragment Master: '5'[1-5]Digit_Two'-'Digit_Four'-'Digit_Four'-'Digit_Four;
fragment Express: '3'('4'|'7')Digit_Two'-'Digit_Four'-'Digit_Four'-'Digit_Four;
fragment Diners_Short: '3'('0'[0-5] | ('6'|'8')Digit)'-'Digit_Three'-'Digit_Four'-'Digit_Four;
fragment Diners_Long: '5'Digit_Three'-'Digit_Four'-'Digit_Four'-'Digit_Four;
fragment Diners: (Diners_Long|Diners_Short);
fragment Discover: ('6011'Twelve|'65'Fourteen);
fragment JCB_Short: ('2131'|'1800')'-'Digit_Four'-'Digit_Four'-'Digit_Three;
fragment JCB_Long: '35'Digit_Two'-'Digit_Four'-'Digit_Four'-'Digit_Four;
fragment JCB: JCB_Long|JCB_Short;
fragment Card: Visa|Master|Express|Diners|Discover|JCB;

WS: [ \r\n\t]+ {skip();} ;   