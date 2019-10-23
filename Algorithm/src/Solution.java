import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Solution {
    public List<Integer> getMaxWindow(int[] array, int size) {
        ArrayList<Integer> result = new ArrayList<>();
        if (size < 1) {
            return result;
        }
        size = Math.min(array.length, size);
        LinkedList<Integer> queue = new LinkedList<>();
        int i = -1;
        int j = 0;
        while (j < array.length) {
            addElement(queue, array[j]);
            if (j - i < size) {
                j++;
                continue;
            }
            result.add(queue.getFirst());
            j++;
            i++;
            if (queue.getFirst() == array[i]) {
                queue.removeFirst();
            }
        }
        return result;
    }

    private void addElement(LinkedList<Integer> queue, int value) {
        while (!queue.isEmpty() && queue.getLast() < value) {
            queue.removeLast();
        }
        queue.addLast(value);
    }
}