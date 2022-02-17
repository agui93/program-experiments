import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * leetcode-3. 无重复字符的最长子串
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 *
 * @author agui93
 * @since 2022/02/17
 */
public class longest_substring_without_repeating_characters {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> win = new HashMap<>();
        int left = 0;
        int right = 0;
        int maxLen = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            win.put(c, win.getOrDefault(c, 0) + 1);
            while (win.get(c) > 1) {
                win.put(s.charAt(left), win.get(s.charAt(left)) - 1);
                left++;
            }

            right++;
            maxLen = Math.max(maxLen, right - left);
        }

        return maxLen;
    }


    public int lengthOfLongestSubstring1(String s) {
        Set<Character> win = new HashSet<>();
        int left = 0;
        int right = 0;
        int maxLen = 0;
        while (right < s.length()) {
            while (win.contains(s.charAt(right))) {
                win.remove(s.charAt(left));
                left++;
            }
            win.add(s.charAt(right));
            right++;

            maxLen = Math.max(maxLen, right - left);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        longest_substring_without_repeating_characters obj = new longest_substring_without_repeating_characters();
        String s = "abcabcbb";
        System.out.println(s + " : " + obj.lengthOfLongestSubstring1(s));
        s = "pwwkew";
        System.out.println(s + " : " + obj.lengthOfLongestSubstring1(s));
    }

}
