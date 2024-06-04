package com.study.effective_software_testing.chap2;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

/**
 * url : https://leetcode.com/problems/add-two-numbers/
 *
 * 23과 42를 더하면 [2,3] 과 [4,2] => [6,5] => 65
 *
 */
public class LeetCode_AddTwoNumbers {

    public List<Integer> add(List<Integer> left, List<Integer> right) {
        if (left == null || right == null)  {
            return null;
        }

        Collections.reverse(left);
        Collections.reverse(right);

        LinkedList<Integer> result = new LinkedList<>();

        int carry = 0;

        for (int i = 0; i < max(left.size(), right.size()); i++) {
            int leftDigit = left.size() > i ? left.get(i) : 0;
            int rightDigit = right.size() > i ? right.get(i) : 0;

            if (leftDigit < 0 || leftDigit > 9 ||
                    rightDigit < 0 || rightDigit > 9) {
                throw new IllegalArgumentException("범위를 벗어남");
            }

            int sum = leftDigit + rightDigit + carry;

            result.addFirst(sum % 10);

            carry = sum / 10;
        }

        return result;
    }
}
