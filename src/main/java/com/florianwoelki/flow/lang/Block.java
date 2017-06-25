package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class Block {

    private final List<CustomLineHandler> handlers;

    private final Block superBlock;
    private final List<Variable> vars;
    private final List<Block> subBlocks;
    private final List<String> lines;

    public Block(Block superBlock) {
        this.handlers = new ArrayList<>();

        this.superBlock = superBlock;
        this.vars = new ArrayList<>();
        this.subBlocks = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    protected abstract void runAfterParse() throws InvalidCodeException;

    public void registerCustomLineHandler(CustomLineHandler h) {
        handlers.add(h);
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public final void doBlocks() throws InvalidCodeException {
        for(Block block : subBlocks) {
            block.run();
        }
    }

    public Block[] getBlockTree() {
        List<Block> tree = new ArrayList<>();

        Block b = this;

        while(b != null) {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public void addVariable(Variable.VariableType t, String name, Object value) {
        vars.add(new Variable(t, name, value));
    }

    public Variable getVariable(String name) throws InvalidCodeException {
        for(Block b : Arrays.copyOfRange(getBlockTree(), 0, getBlockTree().length - 1)) {
            if(b.hasVariable(name)) {
                return b.getVariable(name);
            }
        }

        for(Variable v : vars) {
            if(v.getName().equals(name)) {
                return v;
            }
        }

        throw new InvalidCodeException("Variable " + name + " is not declared.");
    }

    public boolean hasVariable(String name) {
        for(Variable v : vars) {
            if(v.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void run() throws InvalidCodeException {
        subBlocks.clear();

        If lastIf = null;

        Block currentBlock = null;
        int numEndsIgnore = 0;

        lineLoop:
        for(String line : lines) {
            for(CustomLineHandler h : handlers) {
                if(line.startsWith(h.getStart())) {
                    if(h.run(line, this)) {
                        break lineLoop;
                    }

                    continue lineLoop;
                }
            }

            for(ConditionalBlock.ConditionalBlockType bt : ConditionalBlock.ConditionalBlockType.values()) {
                if(line.split(" ")[0].equals(bt.name().toLowerCase())) {
                    if(currentBlock == null) {
                        String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);

                        if(bt == ConditionalBlock.ConditionalBlockType.ELSE) {
                            if(lastIf == null) throw new InvalidCodeException("Else if without if.");

                            currentBlock = new Else(this);
                        } else {
                            String a = args[0], b = args[2];
                            ConditionalBlock.CompareOperation op = ConditionalBlock.CompareOperation.match(args[1]);

                            if(bt == ConditionalBlock.ConditionalBlockType.IF) {
                                currentBlock = new If(this, a, b, op);
                            } else if(bt == ConditionalBlock.ConditionalBlockType.ELSEIF) {
                                if(lastIf == null) throw new InvalidCodeException("Else if without if.");

                                currentBlock = new ElseIf(this, a, b, op);
                            } else if(bt == ConditionalBlock.ConditionalBlockType.WHILE) {
                                currentBlock = new While(this, a, b, op);
                            } else if(bt == ConditionalBlock.ConditionalBlockType.DOWHILE) {
                                currentBlock = new DoWhile(this, a, b, op);
                            } else if(bt == ConditionalBlock.ConditionalBlockType.FOR) {
                                currentBlock = new For(this, a, b);
                            }
                        }
                    } else {
                        currentBlock.addLine(line);
                        numEndsIgnore++;
                    }

                    continue lineLoop;
                }
            }

            if(line.equals("end")) {
                if(numEndsIgnore > 0) {
                    numEndsIgnore--;
                    if(currentBlock != null) {
                        currentBlock.addLine("end");
                    }
                    continue;
                }

                if(currentBlock != null) {
                    currentBlock.addLine("end");
                    if(!(currentBlock instanceof Else)) {
                        subBlocks.add(currentBlock);
                    }

                    if(currentBlock instanceof If) {
                        lastIf = (If) currentBlock;
                    } else if(currentBlock instanceof ElseIf) {
                        lastIf.addElseIf((ElseIf) currentBlock);
                    } else if(currentBlock instanceof Else) {
                        lastIf.setElse((Else) currentBlock);
                        lastIf = null;
                    }

                    currentBlock = null;
                } else {
                    break;
                }
            } else {
                if(currentBlock != null) {
                    currentBlock.addLine(line);
                } else {
                    subBlocks.add(new Line(this, line));
                }
            }
        }

        runAfterParse();
    }

    @Override
    public String toString() {
        return "Block type=" + getClass().getSimpleName();
    }

    public Block getSuperBlock() {
        return superBlock;
    }
}

abstract class CustomLineHandler {

    private final String start;

    public CustomLineHandler(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public abstract boolean run(String line, Block sB) throws InvalidCodeException;

}