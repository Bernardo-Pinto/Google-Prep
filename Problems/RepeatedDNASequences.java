package Problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RepeatedDNASequences {
    final long MOD = 1_000_000_007L;
    final int SEQ_LENGTH = 10;
    final int BASE = 131;

    public List<String> findRepeatedDnaSequences(String s) {
        if(s.length()<11) return new ArrayList<>();
        Map<Long,String> sequences = new HashMap<>();
        HashSet<String> seen = new HashSet<>();

        long highPow = 1;
        for (int i = 0; i < 10; i++) {
            highPow = (highPow * BASE) % MOD;
        }

        String seq = s.substring(0, SEQ_LENGTH);
        long hashedSeqInt = getIntValue(seq);
        sequences.put(hashedSeqInt,seq);

        for(int i = 10;i<s.length();i++){
            char removedChar = seq.charAt(0);
            seq = seq.substring(1) + s.charAt(i);
            hashedSeqInt = getNextValue(hashedSeqInt, SEQ_LENGTH, removedChar, s.charAt(i), highPow);
            //check if it exists in map. If it does, compare the strings letter by letter
            if(sequences.get(hashedSeqInt) != null){
                if(sequences.get(hashedSeqInt).equals(seq) && !seen.contains(seq)){
                    seen.add(seq);
                }
            } else {
                sequences.put(hashedSeqInt, seq);
            }
        }
        return new ArrayList<String>(seen);
    }

    private long getNextValue(long currValue,int length, char oldChar, char nextChar, long highPow){
        return ((currValue * BASE - ((int)oldChar * highPow) % MOD + MOD) % MOD + (int)nextChar) % MOD;
    }

    private long getIntValue(String s){
        long result = 0;
        for(int i=0;i<SEQ_LENGTH;i++){
            result = (result * BASE + (int)s.charAt(i)) % MOD;
        }
        return result;
    }
}
