package Snowpunk.cards.parts;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cutContent.democards.EasyModalChoiceCard;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatingCoils extends EasyModalChoiceCard
{
    public static final String ID = makeID(HeatingCoils.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public HeatingCoils(String name, String description, Runnable onUseOrChosen)
    {
        super(NAME, DESCRIPTION, () ->
        {
            //Add heat to card but idk how to get card to modify yet
        });
    }
}
