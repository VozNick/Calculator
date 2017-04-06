package com.example.vozny.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Boolean isAction = false;
//    private Boolean isNextAction = false;
    private Boolean flagAfterEqual = false;
    private Boolean isPlusMinus = false;
    private Boolean isPlusMinusForEqual = false;
    private Boolean isDot = false;
    private Boolean isInterest = false;
    //    private Boolean isNextInterest = false;
    private char operationAction = 'e';
    private double firstDouble = 0.0;
    private double secondDouble = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tView);
    }

    public void cleanClickButton(View view) {
        textView.setText("");
        textView.setHint("0");
        isAction = false;
//        isNextAction = false;
        flagAfterEqual = false;
        isPlusMinus = false;
        isPlusMinusForEqual = false;
        isDot = false;
        isInterest = false;
//        isNextInterest = false;
        operationAction = 'e';
        firstDouble = 0.0;
        secondDouble = 0.0;
    }

    public void oneClickButton(View view) {
        fromZeroToNine("1", '1');
    }

    public void twoClickButton(View view) {
        fromZeroToNine("2", '2');
    }

    public void threeClickButton(View view) {
        fromZeroToNine("3", '3');
    }

    public void fourClickButton(View view) {
        fromZeroToNine("4", '4');
    }

    public void fiveClickButton(View view) {
        fromZeroToNine("5", '5');
    }

    public void sixClickButton(View view) {
        fromZeroToNine("6", '6');
    }

    public void sevenClickButton(View view) {
        fromZeroToNine("7", '7');
    }

    public void eightClickButton(View view) {
        fromZeroToNine("8", '8');
    }

    public void nineClickButton(View view) {
        fromZeroToNine("9", '9');
    }

    public void zeroClickButton(View view) {
        fromZeroToNine("0", '0');
    }

    public void fromZeroToNine(String num, char numChar) {
        if (!isInterest) {
            if (flagAfterEqual) {
                flagAfterEqual = false;
                textView.setText(num);
            } else {
                String stringOfTextView = textView.getText().toString();
                if (stringOfTextView.length() == 0) {
                    textView.setText(num);
                } else {
                    char[] chars = stringOfTextView.toCharArray();
                    if (!isAction) {
                        if (stringOfTextView.length() == 1) {
                            if (stringOfTextView.equals("0")) {
                                textView.setText(num);
                            } else {
                                textView.append(num);
                            }
                        } else {
                            textView.append(num);
                        }
                    } else {
                        if (stringOfTextView.endsWith(String.valueOf(operationAction))) {
                            textView.append(num);
                        } else {
                            for (int i = 0; i < chars.length; i++) {
                                if (chars[i] == operationAction) {
                                    if (chars[i + 1] == '0') {
                                        if (chars[i + 2] == '.') {
                                            textView.setText(String.valueOf(chars));
                                            textView.append(num);
                                        } else {
                                            chars[i + 1] = numChar;
                                            textView.setText(String.valueOf(chars));
                                        }
                                    } else {
                                        textView.append(num);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void plusClickButton(View view) {
        fourActions("+", ")+", '+');
    }

    public void minusClickButton(View view) {
        fourActions("-", ")-", '-');
    }

    public void multiplyClickButton(View view) {
        fourActions("*", ")*", '*');
    }

    public void divisionClickButton(View view) {
        fourActions("/", ")/", '/');
    }

    public void fourActions(String num, String num1, char numChar) {
        if (!isPlusMinus) {
            if (!(textView.getText().toString().length() == 0)) {
                isDot = false;
                if (!textView.getText().toString().endsWith(String.valueOf(operationAction))) {
                    if (!flagAfterEqual) {
                        if (!isAction) {
                            isAction = true;
                            operationAction = numChar;
                            textView.append(num);
                        }
                    } else {
                        flagAfterEqual = false;
                        isAction = true;
                        operationAction = numChar;
                        textView.append(num);
                    }
                }
            }
        } else {
            flagAfterEqual = false;
            isAction = true;
            operationAction = numChar;
            textView.append(num1);
            isPlusMinus = false;
        }
    }

    public void equalsClickButton(View view) {
        try {
            String text = (textView.getText().toString());
            if (text.endsWith(String.valueOf(operationAction))) {
                text = text.substring(0, text.length() - 1);
            }
            if (text.endsWith(String.valueOf('.'))) {
                text = "0";
            }
            if (isPlusMinus) {
                text += ")";
            }
            if (!flagAfterEqual) {
                flagAfterEqual = true;
                isAction = false;
                if (!isPlusMinusForEqual) {
                    if (isInterest) {
                        text = text.substring(0, text.length() - 1);
                    }
                    char[] chars = text.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == operationAction) {
                            firstDouble = Double.parseDouble(text.substring(0, i));
                            secondDouble = Double.parseDouble(text.substring(i + 1));
                            if (!isInterest) {
                                chooseOperationForEquals(firstDouble, secondDouble);
                            } else {
                                double interest = firstDouble * secondDouble / 100;
                                chooseOperationForEquals(firstDouble, interest);
                            }
                            return;
                        }
                    }
                    operationAction = 'e';
                } else {
                    char[] chars = text.toCharArray();
                    Boolean containsFirst = text.contains(")+");
                    Boolean containsSecond = text.contains("+(");
                    Boolean containsBoth = text.contains(")" + operationAction + "(");
                    if (containsBoth) {
                        for (int i = 0; i < chars.length; i++) {
                            if (chars[i] == ')') {
                                firstDouble = Double.parseDouble(text.substring(1, i));
                                if (!isInterest) {
                                    plusMinusEqual(false, text, i, 3, 1);
                                } else {
                                    plusMinusEqual(true, text, i, 3, 2);
                                }
                                return;
                            }
                        }
                    } else if (containsFirst) {
                        for (int i = 0; i < chars.length; i++) {
                            if (chars[i] == ')') {
                                firstDouble = Double.parseDouble(text.substring(1, i));
                                if (!isInterest) {
                                    plusMinusEqual(false, text, i, 2, 0);
                                } else {
                                    plusMinusEqual(true, text, i, 2, 1);
                                }
                                return;
                            }
                        }
                    } else if (containsSecond) {
                        for (int i = 0; i < chars.length; i++) {
                            if (chars[i] == operationAction) {
                                firstDouble = Double.parseDouble(text.substring(0, i));
                                if (!isInterest) {
                                    plusMinusEqual(false, text, i, 2, 1);
                                } else {
                                    plusMinusEqual(true, text, i, 2, 2);
                                }
                                return;
                            }
                        }
                    }
                }
            }
            isPlusMinus = false;
            isDot = false;
            isInterest = false;
//            isNextInterest = false;
            operationAction = 'e';
            isPlusMinusForEqual = false;
        } catch (NumberFormatException e) {
            textView.setText("Wrong input");
        }
    }

    public void plusMinusEqual(Boolean flag, String text, int one, int two, int three) {
        if (!flag) {
            secondDouble = Double.parseDouble(text.substring(one + two, (text.length() - three)));
            chooseOperationForEquals(firstDouble, secondDouble);
        } else {
            secondDouble = Double.parseDouble(text.substring(one + two, (text.length() - three)));
            double interest = firstDouble * secondDouble / 100;
            chooseOperationForEquals(firstDouble, interest);
        }
    }

    public void chooseOperationForEquals(double first, double second) {
        double result = 0.0;
        if (operationAction == '+') {
            result = first + second;
        } else if (operationAction == '-') {
            result = first - second;
        } else if (operationAction == '*') {
            result = first * second;
        } else if (operationAction == '/') {
            result = first / second;
        }
        if (result % 1 == 0) {
            textView.setText(String.valueOf((int) result));
        } else {
            textView.setText(String.valueOf(result));
        }
    }


    public void plusMinusClickButton(View view) {
        isPlusMinusForEqual = true;
        if (!isInterest) {
            if (!isAction) {
                if (!isPlusMinus) {
                    isPlusMinus = true;
                    if (flagAfterEqual) {
                        flagAfterEqual = false;
                        textView.setText("(-0");
                    } else {
                        String s = "(-";
                        String d = textView.getText().toString();
                        String f = s + d;
                        textView.setText(f);
                    }
                } else {
                    isPlusMinus = false;
                    String s = (textView.getText().toString()).substring(2);
                    textView.setText(s);
                }
            } else {
                if (!isPlusMinus) {
                    isPlusMinus = true;
                    char[] chars = (textView.getText().toString()).toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == operationAction) {
                            String s = "(-";
                            String d = (textView.getText().toString()).substring(0, i + 1);
                            String f = (textView.getText().toString()).substring(i + 1);
                            String g = s + f;
                            s = d + g;
                            textView.setText(s);
                        }
                    }
                } else {
                    isPlusMinus = false;
                    char[] chars = (textView.getText().toString()).toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == operationAction) {
                            String s = (textView.getText().toString()).substring(0, i + 1);
                            String d = (textView.getText().toString()).substring(i + 1);
                            String f = d.substring(2);
                            String g = s + f;
                            textView.setText(g);
                        }
                    }
                }
            }
        }
    }

    public void dotClickButton(View view) {
        if (!isInterest) {
            if (!isDot) {
                isDot = true;
                if (flagAfterEqual) {
                    flagAfterEqual = false;
                    textView.setText("0.");
                } else {
                    String stringOfTextView = textView.getText().toString();
                    if (stringOfTextView.length() == 0) {
                        textView.setText("0.");
                    } else {
                        if (!isAction) {
                            textView.append(".");
                        } else {
                            if (stringOfTextView.endsWith(String.valueOf(operationAction))) {
                                textView.append("0.");
                            } else {
                                textView.append(".");
                            }
                        }
                    }
                }
            }
        }
    }

    public void interestClickButton(View view) {
        if (!isInterest) {
            if (!(textView.getText().toString().length() == 0)) {
                isInterest = true;
                if (!textView.getText().toString().endsWith(String.valueOf(operationAction))) {
                    if (!isAction) {
                        textView.setText("0");
                        isInterest = false;
                    } else {
                        textView.append("%");
                    }
                } else {
                    textView.setText("");
                    cleanClickButton(view);
                }
            }
        }
    }
}
