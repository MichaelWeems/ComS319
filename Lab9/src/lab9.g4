lexer grammar lab9;

// GENERAL
fragment DIGIT: [0123456789];
fragment TWO_DIGITS : DIGIT DIGIT ;
fragment THREE_DIGITS : DIGIT DIGIT DIGIT ;
fragment FOUR_DIGITS : DIGIT DIGIT DIGIT DIGIT ;

fragment ALPHA: [a-zA-Z] ;
fragment X_EXCLUDE: [a-wy-zA-WY-Z] ;

///////////////////////////////////
// NAME

fragment TAG_OPEN : '<' ;
fragment TAG_CLOSE : '>' ;
fragment TAG_END : '</' ;

fragment NAME_SPECIALS : DIGIT | '-' | '_' | '.' ;

fragment NAME_CHARS : ALPHA | NAME_SPECIALS ;

fragment NAME_START : ('_' | X_EXCLUDE ) ;

fragment NAME: NAME_START (NAME_CHARS)*? ;


TAG: ( TAG_OPEN | TAG_END ) NAME TAG_CLOSE  
	{ System.out.println("Name: " + getText()); }
        ;
		
///////////////////////////////////
// Email

// localpart
fragment LOCALPART :  (ALPHA | LOCALPART_CHARS)+ (PERIOD? (ALPHA | LOCALPART_CHARS)+)*;
fragment LOCALPART_CHARS : [-_~!$&'()*+,;=:] ;
fragment PERIOD : '.' ;


// domainpart
fragment DOMAINPART : DOMAINPART_CHARS+ ;
fragment DOMAINPART_CHARS : ALPHA | DIGIT | '-' | '.' ;

fragment EMAIL : LOCALPART '@' DOMAINPART;

EMAIL_BLOCK: EMAIL
             { System.out.println("Email: " + getText()); }
        ;

///////////////////////////////////
// Date

fragment DAY_ONE : '0' [1-9] ;
fragment DAY_TENS : [1-2] [0-9] ;
fragment DAY_MAX : '3' [0-1] ;

fragment DAY : DAY_ONE | DAY_TENS | DAY_MAX ;

fragment MONTH_ONE :  '0' [1-9];
fragment MONTH_TEN :  '1' [0-2];

fragment MONTH : MONTH_ONE | MONTH_TEN ;

fragment YEAR_START :  '20' [0-9] [0-9] ;
fragment YEAR_MAX : '2100' ;

fragment YEAR : YEAR_START | YEAR_MAX ;


fragment DATE : DAY '/' MONTH '/' YEAR;

DATE_BLOCK: DATE
             { System.out.println("Date: " + getText()); }
        ;
	
///////////////////////////////////
// Phone

fragment PHONE_DASHED : THREE_DIGITS '-' THREE_DIGITS '-' FOUR_DIGITS ;
fragment PHONE_BRACKETED : '(' THREE_DIGITS ') ' THREE_DIGITS '-' FOUR_DIGITS ;
fragment PHONE_SPACED : THREE_DIGITS ' ' THREE_DIGITS ' ' FOUR_DIGITS ;
fragment PHONE_DOTTED : THREE_DIGITS '.' THREE_DIGITS '.' FOUR_DIGITS ;

fragment PHONE : PHONE_BRACKETED | PHONE_DASHED | PHONE_DOTTED | PHONE_SPACED ;

PHONE_BLOCK: PHONE
             { System.out.println("PHONE: " + getText()); }
        ;
			
			
///////////////////////////////////
// Credit Card

fragment VISA : '4' (VISA_NEW | VISA_OLD) ;
fragment VISA_NEW : THREE_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS ;
fragment VISA_OLD : TWO_DIGITS '-' THREE_DIGITS '-' THREE_DIGITS '-' FOUR_DIGITS;

fragment MASTERCARD: [51-55] TWO_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS;

fragment AMERICAN_EXPRESS:  ('34' | '37') TWO_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS '-' THREE_DIGITS;

fragment DINERS_CLUB : ( [300-305] | (('36' | '38')  DIGIT )) '-' THREE_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS;

fragment DISCOVER : ('6011' | '65' TWO_DIGITS) '-' FOUR_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS;

fragment JCB_15 : ( '2131' | '1800' ) '-' FOUR_DIGITS '-' FOUR_DIGITS '-' THREE_DIGITS ;
fragment JCB_16 : '35' TWO_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS '-' FOUR_DIGITS ;
fragment JCB : JCB_15 | JCB_16 ;

fragment CREDIT_CARD : VISA | MASTERCARD | AMERICAN_EXPRESS | DINERS_CLUB | DISCOVER | JCB ;

JCB_BLOCK: CREDIT_CARD
             { System.out.println("Credit Card: " + getText()); }
        ;		

		
		
WS: [ \r\n\t]+         {skip();} ;   
