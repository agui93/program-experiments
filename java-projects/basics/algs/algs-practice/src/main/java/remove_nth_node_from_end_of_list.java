import java.util.Arrays;
import java.util.List;

/**
 * leetcode 19. 删除链表的倒数第 N 个结点
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * @author agui93
 * @since 2022/02/17
 */
public class remove_nth_node_from_end_of_list {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode fast = dummy;
        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;
            if (i != n && fast == null) {//兼容err
                return null;
            }
        }

        ListNode slow = dummy;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        ListNode node = slow.next;
        slow.next = node.next;
        node.next = null;

        return dummy.next;
    }


    public static ListNode buildListNode(List<Integer> data) {
        ListNode head = new ListNode(data.get(data.size() - 1));
        for (int i = data.size() - 2; i >= 0; i--) {
            ListNode node = new ListNode(data.get(i));
            node.next = head;
            head = node;
        }
        return head;
    }

    public static void printNodes(ListNode head) {
        ListNode node = head;
        while (node != null) {
            System.out.print("->" + node.val);
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = buildListNode(Arrays.asList(1, 2, 3, 4, 5));
        printNodes(head);

        remove_nth_node_from_end_of_list obj = new remove_nth_node_from_end_of_list();
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 1));
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 2));
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 3));
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 4));
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 5));
        printNodes(obj.removeNthFromEnd(buildListNode(Arrays.asList(1, 2, 3, 4, 5)), 6));
    }
}
