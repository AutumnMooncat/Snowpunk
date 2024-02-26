package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.AssembledPower;
import Snowpunk.powers.HollyPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Randomizer extends CoreCard {
    public static final String ID = makeID(Randomizer.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public Randomizer() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.POST_POW);
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(2);
        return turns;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new ApplyPowerAction(player, player, new AssembledPower(player, 1, card.cores.get(0).magicNumber, card)));
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (!coreCards.get(coreCards.size() - 1).effectTags.contains(EffectTag.POW))
            return true;
        return false;
    }

    @Override
    public void onPowerTrigger(int amount) {
        int rare = AbstractDungeon.cardRandomRng.random(0, 4);
        AbstractCard card;
        switch (rare) {
            case 1:
                card = CardLibrary.getAnyColorCard(CardType.ATTACK, CardRarity.UNCOMMON);
                break;
            case 2:
                card = CardLibrary.getAnyColorCard(CardType.ATTACK, CardRarity.RARE);
                break;
            default:
                card = CardLibrary.getAnyColorCard(CardType.ATTACK, CardRarity.COMMON);
        }
        addToBot(new MakeTempCardInHandAction(card, true));
    }

    @Override
    public String powerIcon() {
        return "draw2";
    }

    @Override
    public String[] getExtended() {
        return TEXT;
    }

    @Override
    public int getUpgradeAmount() {
        return 2;
    }
}
