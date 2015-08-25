package com.lily.dap.util.pinyin4j;

import junit.framework.TestCase;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinHelperTest extends TestCase
{
    public void testToTongyongPinyinStringArray()
    {
        // any input of non-Chinese characters will return null
        {
            assertNull(PinyinHelper.toTongyongPinyinStringArray('A'));
            assertNull(PinyinHelper.toTongyongPinyinStringArray('z'));
            assertNull(PinyinHelper.toTongyongPinyinStringArray(','));
            assertNull(PinyinHelper.toTongyongPinyinStringArray('°£'));
        }

        // Chinese characters
        // single pronounciation
        {
            String[] expectedPinyinArray = new String[] { "li3" };
            String[] pinyinArray = PinyinHelper.toTongyongPinyinStringArray('¿Ó');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "ciou2" };
            String[] pinyinArray = PinyinHelper.toTongyongPinyinStringArray('«Ú');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "jhuang1" };
            String[] pinyinArray = PinyinHelper.toTongyongPinyinStringArray('◊Æ');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        // multiple pronounciations
        {
            String[] expectedPinyinArray = new String[] { "chuan2", "jhuan4" };
            String[] pinyinArray = PinyinHelper.toTongyongPinyinStringArray('¥´');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "lyu4", "lu4" };
            String[] pinyinArray = PinyinHelper.toTongyongPinyinStringArray('¬Ã');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
    }

    public void testToWadeGilesPinyinStringArray()
    {
        // any input of non-Chinese characters will return null
        {
            assertNull(PinyinHelper.toWadeGilesPinyinStringArray('A'));
            assertNull(PinyinHelper.toWadeGilesPinyinStringArray('z'));
            assertNull(PinyinHelper.toWadeGilesPinyinStringArray(','));
            assertNull(PinyinHelper.toWadeGilesPinyinStringArray('°£'));
        }

        // Chinese characters
        // single pronounciation
        {
            String[] expectedPinyinArray = new String[] { "li3" };
            String[] pinyinArray = PinyinHelper.toWadeGilesPinyinStringArray('¿Ó');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "ch`iu2" };
            String[] pinyinArray = PinyinHelper.toWadeGilesPinyinStringArray('«Ú');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "chuang1" };
            String[] pinyinArray = PinyinHelper.toWadeGilesPinyinStringArray('◊Æ');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        // multiple pronounciations
        {
            String[] expectedPinyinArray = new String[] { "ch`uan2", "chuan4" };
            String[] pinyinArray = PinyinHelper.toWadeGilesPinyinStringArray('¥´');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "lu:4", "lu4" };
            String[] pinyinArray = PinyinHelper.toWadeGilesPinyinStringArray('¬Ã');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
    }

    public void testToMPS2PinyinStringArray()
    {
        // any input of non-Chinese characters will return null
        {
            assertNull(PinyinHelper.toMPS2PinyinStringArray('A'));
            assertNull(PinyinHelper.toMPS2PinyinStringArray('z'));
            assertNull(PinyinHelper.toMPS2PinyinStringArray(','));
            assertNull(PinyinHelper.toMPS2PinyinStringArray('°£'));
        }

        // Chinese characters
        // single pronounciation
        {
            String[] expectedPinyinArray = new String[] { "li3" };
            String[] pinyinArray = PinyinHelper.toMPS2PinyinStringArray('¿Ó');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "chiou2" };
            String[] pinyinArray = PinyinHelper.toMPS2PinyinStringArray('«Ú');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "juang1" };
            String[] pinyinArray = PinyinHelper.toMPS2PinyinStringArray('◊Æ');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        // multiple pronounciations
        {
            String[] expectedPinyinArray = new String[] { "chuan2", "juan4" };
            String[] pinyinArray = PinyinHelper.toMPS2PinyinStringArray('¥´');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "liu4", "lu4" };
            String[] pinyinArray = PinyinHelper.toMPS2PinyinStringArray('¬Ã');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
    }

    public void testToYalePinyinStringArray()
    {
        // any input of non-Chinese characters will return null
        {
            assertNull(PinyinHelper.toYalePinyinStringArray('A'));
            assertNull(PinyinHelper.toYalePinyinStringArray('z'));
            assertNull(PinyinHelper.toYalePinyinStringArray(','));
            assertNull(PinyinHelper.toYalePinyinStringArray('°£'));
        }

        // Chinese characters
        // single pronounciation
        {
            String[] expectedPinyinArray = new String[] { "li3" };
            String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('¿Ó');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "chyou2" };
            String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('«Ú');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "jwang1" };
            String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('◊Æ');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        // multiple pronounciations
        {
            String[] expectedPinyinArray = new String[] { "chwan2", "jwan4" };
            String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('¥´');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "lyu4", "lu4" };
            String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('¬Ã');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
    }

    public void testToGwoyeuRomatzyhStringArray()
    {
        // any input of non-Chinese characters will return null
        {
            assertNull(PinyinHelper.toGwoyeuRomatzyhStringArray('A'));
            assertNull(PinyinHelper.toGwoyeuRomatzyhStringArray('z'));
            assertNull(PinyinHelper.toGwoyeuRomatzyhStringArray(','));
            assertNull(PinyinHelper.toGwoyeuRomatzyhStringArray('°£'));
        }

        // Chinese characters
        // single pronounciation
        {
            String[] expectedPinyinArray = new String[] { "lii" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('¿Ó');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "chyou" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('«Ú');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
        {
            String[] expectedPinyinArray = new String[] { "juang" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('◊Æ');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "fuh" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('∏∂');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        // multiple pronounciations
        {
            String[] expectedPinyinArray = new String[] { "chwan", "juann" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('¥´');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { ".me", ".mha", "iau" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('√¥');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }

        {
            String[] expectedPinyinArray = new String[] { "liuh", "luh" };
            String[] pinyinArray = PinyinHelper.toGwoyeuRomatzyhStringArray('¬Ã');

            assertEquals(expectedPinyinArray.length, pinyinArray.length);

            for (int i = 0; i < expectedPinyinArray.length; i++)
            {
                assertEquals(expectedPinyinArray[i], pinyinArray[i]);
            }
        }
    }

    public void testToHanyuPinyinStringArray()
    {

        // any input of non-Chinese characters will return null
        {
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            try
            {
                assertNull(PinyinHelper.toHanyuPinyinStringArray('A', defaultFormat));
                assertNull(PinyinHelper.toHanyuPinyinStringArray('z', defaultFormat));
                assertNull(PinyinHelper.toHanyuPinyinStringArray(',', defaultFormat));
                assertNull(PinyinHelper.toHanyuPinyinStringArray('°£', defaultFormat));
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }

        // Chinese characters
        // single pronounciation
        {
            try
            {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

                String[] expectedPinyinArray = new String[] { "li3" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('¿Ó', defaultFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }
        {
            try
            {
                HanyuPinyinOutputFormat upperCaseFormat = new HanyuPinyinOutputFormat();
                upperCaseFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);

                String[] expectedPinyinArray = new String[] { "LI3" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('¿Ó', upperCaseFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }
        {
            try
            {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

                String[] expectedPinyinArray = new String[] { "lu:3" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('¬¿', defaultFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }
        {
            try
            {
                HanyuPinyinOutputFormat vCharFormat = new HanyuPinyinOutputFormat();
                vCharFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

                String[] expectedPinyinArray = new String[] { "lv3" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('¬¿', vCharFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }

        // multiple pronounciations
        {
            try
            {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

                String[] expectedPinyinArray = new String[] { "jian1", "jian4" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('º‰', defaultFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }

        {
            try
            {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

                String[] expectedPinyinArray = new String[] { "hao3", "hao4" };
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('∫√', defaultFormat);

                assertEquals(expectedPinyinArray.length, pinyinArray.length);

                for (int i = 0; i < expectedPinyinArray.length; i++)
                {
                    assertEquals(expectedPinyinArray[i], pinyinArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * test for combination of output formats
//     */
//    public void testOutputCombination()
//    {
//        try
//        {
//            HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//
//            // fix case type to lowercase firstly, change VChar and Tone
//            // combination
//            outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//
//            // WITH_U_AND_COLON and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("lu:3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_V and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("lv3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_UNICODE and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("l®π3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // // WITH_U_AND_COLON and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("lu:", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_V and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("lv", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_UNICODE and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("l®π", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_AND_COLON and WITH_TONE_MARK is forbidden
//
//            // WITH_V and WITH_TONE_MARK is forbidden
//
//            // WITH_U_UNICODE and WITH_TONE_MARK
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
//
//            assertEquals("l®∑", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // fix case type to UPPERCASE, change VChar and Tone
//            // combination
//            outputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//
//            // WITH_U_AND_COLON and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("LU:3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_V and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("LV3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_UNICODE and WITH_TONE_NUMBER
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//
//            assertEquals("L®π3", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // // WITH_U_AND_COLON and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("LU:", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_V and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("LV", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_UNICODE and WITHOUT_TONE
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//
//            assertEquals("L®π", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//
//            // WITH_U_AND_COLON and WITH_TONE_MARK is forbidden
//
//            // WITH_V and WITH_TONE_MARK is forbidden
//
//            // WITH_U_UNICODE and WITH_TONE_MARK
//            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
//
//            assertEquals("L®∑", PinyinHelper.toHanyuPinyinStringArray('¬¿', outputFormat)[0]);
//        } catch (BadHanyuPinyinOutputFormatCombination e)
//        {
//            e.printStackTrace();
//        }
//    }
}
