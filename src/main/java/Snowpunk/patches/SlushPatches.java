package Snowpunk.patches;

public class SlushPatches {
/*
    @SpirePatch2(clz = DrawCardAction.class, method = "update")
    public static class PreventReshuffle {
        @SpireInsertPatch(
                rloc = 5,
                localvars = {"deckSize"}
        )
        public static void update(DrawCardAction __instance) {
            for (AbstractCard card: AbstractDungeon.player.hand.group) {
                if(card instanceof Slush){
                    __instance.isDone = true;// 162
                    AbstractGameAction followUpAction = ReflectionHacks.getPrivate(__instance, DrawCardAction.class, "followUpAction");
                    if (followUpAction != null) {// 163
                        Wiz.att(followUpAction);
                    }
                    Wiz.att(new TalkAction(true, Slush.cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));
                    SpireReturn.Return();
                }
            }
        }
    }*/
}
