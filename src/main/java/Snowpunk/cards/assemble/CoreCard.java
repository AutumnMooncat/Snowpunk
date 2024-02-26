package Snowpunk.cards.assemble;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public abstract class CoreCard extends AbstractMultiUpgradeCard {

    public CoreCard(String cardID, int cost, CardType type, CoreCard.EffectTag... effectTags) {
        super(cardID, cost, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.effectTags = new ArrayList<>(Arrays.asList(effectTags));
        TypeOverridePatch.setOverride(this, KEYWORD_TEXT[0]);
        tags.add(CardTags.HEALING);
    }

    //region Spawn Conditions
    public boolean canSpawn(AssembledCard card, ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(c -> c.getClass().equals(this.getClass())))
            return false;
        if (baseDamage > -1 && coreCards.stream().anyMatch(c -> c.baseDamage > -1))
            return false;
        if (baseBlock > -1 && coreCards.stream().anyMatch(c -> c.baseBlock > -1))
            return false;
        if (baseMagicNumber > -1 && coreCards.stream().anyMatch(c -> c.baseMagicNumber > -1))
            return false;
        if (baseSecondMagic > -1 && coreCards.stream().anyMatch(c -> c.baseSecondMagic > -1))
            return false;
        if (unavailableTurns().contains(coreCards.size()))
            return false;
        /*if(card.getCost() >= 2 && cost > 0)
            return false;*/
        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.POW)) && (target == CardTarget.ENEMY || target == CardTarget.ALL_ENEMY))
            return false;
        if (coreCards.size() == 1 && coreCards.get(0).effectTags.contains(EffectTag.POW) && type != CardType.POWER)
            return false;
        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.DBF)) && baseDamage > -1)
            return false;
        if (getCustomCANTSpawnCondition(coreCards))
            return false;
        return true;
    }

    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        return false;
    }
    //endregion

    //region Modifiable Stuff
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {

    }

    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        return turns;
    }

    public void setStats(AbstractCard card) {
    }
    //endregion

    //region Non-Modifiable Stuff
    public enum EffectTag {
        DMG,
        POW,
        POST_POW,
        BUF,
        DBF,
        BLK,
        CLK,
        CRD,
        MOD,
        EXH,
        MGC,
        CORE,
        NAME,
        ADJECTIVE
    }

    public enum PowerCondition {
        HOT,
        CLANK,
        DRAW,
        PLAY,
        END,
        START,
        DBF,
        BLK
    }

    public static String[] KEYWORD_TEXT = CardCrawlGame.languagePack.getUIString(makeID("Cores")).TEXT;
    private static ArrayList<TooltipInfo> coreTooltip;

    public final ArrayList<CoreCard.EffectTag> effectTags;

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (coreTooltip == null) {
            coreTooltip = new ArrayList<>();
            coreTooltip.add(new TooltipInfo(KEYWORD_TEXT[1], KEYWORD_TEXT[2]));
        }
        return coreTooltip;
    }

    @Override
    public void upp() {
    }


    @Override
    public void addUpgrades() {
    }

    public void setUpgrade(AbstractMultiUpgradeCard card) {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onChoseThisOption() {
    }

    public void apply(AssembledCard card) {
        card.cores.add(this);
    }

    public int getUpgradeAmount() {
        return -1;
    }

    public boolean costUpgrade() {
        return false;
    }

    public boolean gearUpgrade() {
        return false;
    }

    public void onClank(AssembledCard card) {
    }

    public void onPowerTrigger(int amount) {
    }

    public void onPowerTrigger(AbstractCard card, int amount) {
    }

    public void onPowerTrigger(AbstractCard card, int amount, UseCardAction action) {
        onPowerTrigger(card, amount);
    }

    public String powerIcon() {
        return "tools";
    }

    public PowerCondition getPower() {
        return null;
    }

    public String[] getExtended() {
        return new String[]{""};
    }
    //endregion
}
