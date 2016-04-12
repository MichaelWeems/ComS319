grammar lab10;

@header {
    import java.util.*;
}

@members {
    Boolean relation = false;
    Stack<String> Stack = new Stack<String>();
    int tempI = 0;
    
    void result(){
        while(!Stack.empty()){
            System.out.print(Stack.pop()+";");
        } 
    };
    
    void push(int num){
        Stack.push(Integer.toString(num));
    };
    
    int popI(){
        return Integer.parseInt(Stack.pop());
    };
    
    void push(boolean rela){
        if(rela)
            Stack.push("true");
        else
            Stack.push("false");
    };
    
    Boolean popB(){
        String temp = Stack.pop().toLowerCase();
        if(temp.equals("true"))
            return true;
        else
            return false;
    };
}

start: state* ;

state @after {result(); }: expr ';' ;

Digit: [0-9]+;
Boolean: [tT][rR][uU][eE]|[fF][aA][lL][sS][eE];
WS : [ \r\t\n]+ -> skip ;

add @after {tempI = popI(); tempI = popI() + tempI; push(tempI);}: '+' ; 
sub @after {tempI = popI(); tempI = popI() - tempI; push(tempI);}: '-' ; 
mul @after {tempI = popI(); tempI = popI() * tempI; push(tempI);}: '*' ;
div @after {tempI = popI(); tempI = popI() / tempI; push(tempI);}: '/' ;
rem @after {tempI = popI(); tempI = popI() % tempI; push(tempI);}: '%' ;
numerical: add|sub|div|mul|rem;

and @after {relation = popB(); relation = popB() && relation; push(relation);}: '&&';
or @after {relation = popB(); relation = popB() || relation; push(relation);}: '||' ;
not: '!' ;
logical: and|or|not;

less @after {tempI = popI(); relation = popI() < tempI; push(relation);}: '<' ;
great @after {tempI = popI(); relation = popI() > tempI; push(relation);}: '>' ;
equal @after {tempI = popI(); relation = popI() == tempI; push(relation);}: '==' ;
lessEqual @after {tempI = popI(); relation = popI() <= tempI; push(relation);}: '<=' ;
greatEqual @after {tempI = popI(); relation = popI() >= tempI; push(relation);}: '>=' ;
notEqual @after {tempI = popI(); relation = popI() != tempI; push(relation);}: '!=' ;
relational: less|great|equal|lessEqual|greatEqual|notEqual;

oper: numerical|logical|relational;
integer: Digit;
bool: Boolean;
value: integer|bool;
expr: expr expr oper|value{Stack.push($value.text);};