package de.thm.hcia.twofactorlockscreen.security;

import group.pals.android.lib.ui.lockpattern.util.IEncrypter;
import group.pals.android.lib.ui.lockpattern.widget.LockPatternUtils;
import group.pals.android.lib.ui.lockpattern.widget.LockPatternView.Cell;

import java.util.List;
import java.util.zip.CRC32;

import android.content.Context;

public class LPEncrypter implements IEncrypter {


    public char[] encryptSha(Context context, List<Cell> pattern) {
        /*
         * We just calculate CRC-32 of the pattern, then return it.
         */
        CRC32 c = new CRC32();
        c.update(LockPatternUtils.patternToSha1(pattern).getBytes());
        return String.format("%08x", c.getValue()).toCharArray();
    }// encrypt()

	@Override
	public char[] encrypt(Context context, char[] pattern) {
		// TODO Auto-generated method stub
		
		return null;
	}
}