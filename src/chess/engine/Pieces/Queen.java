package chess.engine.Pieces;


import chess.engine.Alliance;
import chess.engine.Board.Board;
import chess.engine.Board.BoardUtils;
import chess.engine.Move.Attack_Move;
import chess.engine.Move.Major_Move;
import chess.engine.Move.Move;
import chess.engine.Board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{

    private final int[] candidateCoordinate = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance , Piece_Type.QUEEN);
    }


    public List<Move> calculateLegalMoves(Board board) {

        List<Move> legalMove = new ArrayList<Move>() ;

        for(int candidateDestination : candidateCoordinate)
        {
            int candidateDestinationCoordinate = this.getPiecePosition() ;
            while(BoardUtils.isValidCoordinate(candidateDestinationCoordinate))
            {
                if(firstColumnExclusion(candidateDestinationCoordinate,candidateDestination)
                        || eighthColumnExclusion(candidateDestinationCoordinate,candidateDestination))
                    break ;

                candidateDestinationCoordinate += candidateDestination ;
                if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate))
                {
                    Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate) ;
                    if(!candidateDestinationTile.isTileOccupied())
                        legalMove.add(new Major_Move(board , candidateDestinationCoordinate , this));
                    else
                    {
                        Piece candidateDestinationPiece = candidateDestinationTile.getPiece() ;
                        Alliance candidateDestinationAlliance = candidateDestinationPiece.getAlliance() ;
                        if(this.getAlliance() != candidateDestinationAlliance)
                            legalMove.add(new Attack_Move(board , candidateDestinationCoordinate , this , candidateDestinationPiece ));
                    }
                    break ;
                }
            }

        }

        if(legalMove != null)
            return ImmutableList.copyOf(legalMove);
        throw new NullPointerException("Error in Queen's legal moves") ;
    }

    public Queen movePiece(Move move) {
        return new Queen(move.getDestinationCoordinate() , move.getMovedPiece().getAlliance());
    }

    private static boolean firstColumnExclusion(int currentCoordinate , int candidateCoordinate)
    {
        return BoardUtils.FIRST_COLUMN[currentCoordinate] &&
                (candidateCoordinate == -1
                || candidateCoordinate == -9
                || candidateCoordinate == 7) ;
    }
    private  static boolean eighthColumnExclusion(int currentCoordinate , int candidateCoordinate)
    {
        return BoardUtils.EIGHTH_COLUMN[currentCoordinate] &&
                (candidateCoordinate == 1
                || candidateCoordinate == 9
                || candidateCoordinate == -7) ;
    }

    @Override
    public String toString() {
        return Piece_Type.QUEEN.toString();
    }
}
