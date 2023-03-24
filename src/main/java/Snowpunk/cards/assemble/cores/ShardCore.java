package Snowpunk.cards.assemble.cores;

/*
@NoPools
@NoCompendium
public class ShardCore extends CoreCard {
    public static final String ID = makeID(ShardCore.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, MAGIC = 1;

    public ShardCore() {
        super(ID, COST, TYPE, EffectTag.MOD);
        magicNumber = baseMagicNumber = MAGIC;
        MultiCardPreview.add(this, new IceShard());
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new MakeTempCardInHandAction(new IceShard(), card.magicNumber));
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(1);
        return turns;
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        int totalCost = 0;
        for (CoreCard core : coreCards) {
            totalCost += core.cost;
        }
        for (CoreCard core : coreCards) {
            if (CardTemperatureFields.getCardHeat(core) > 0)
                return true;
        }
        return totalCost < 2;
    }

    @Override
    public void setStats(AbstractCard card) {
        boolean added = false;
        for (AbstractCard preview : MultiCardPreview.multiCardPreview.get(card)) {
            if (preview instanceof IceShard)
                added = true;
        }
        if (!added)
            MultiCardPreview.add(card, new IceShard());
    }
}
*/