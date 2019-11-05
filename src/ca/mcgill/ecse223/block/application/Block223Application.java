package ca.mcgill.ecse223.block.application;


import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.*;

public class Block223Application {

	private static Block223 block223=null;
	private static UserRole currentUserRole=null;
	private static Game currentGame=null;
	private static PlayedGame currentPlayableGame=null;
	private static Block223PlayModeInterface currentGamePlayPage=null;


	public static void main(String[] args) {
		// start UI

		block223 = Block223Persistence.load();
        //openGamePlayPage();
		openLoginPage();
		//openFinishGamePage();
		//openDeadPage();
		//Block223Controller.music();
	}
	public static void openLoginPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
	}

	public static void openSignUpPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUpPage().setVisible(true);
            }
        });
	}
	public static void openEditorMenu() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorMenu().setVisible(true);
            }
        });
	}

	public static void openDefineGamePage() {
		  java.awt.EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            new DefineGamePage().setVisible(true);
		        }
		  });
	}

	public static void openBlockSettingPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BlockSettingPage().setVisible(true);
            }
        });
	}

	public static void openUpdateGamePage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateGamePage().setVisible(true);
            }
        });
	}

	public static void openDeadPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new DeadPageDemo();
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}

	public static void openFinishGamePage() {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new FinishGamePageDemo();
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
           }
        });
	}


	public static void openHallOfFamePage() {
	java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() {
             try {
				new HallOfFamePageDemo();
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
      });
}

//	public static void openLevelFinishedPage() {
//		java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LevelFinishedPage();
//            }
//        });
//	}

	public static void openPausePage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PausePage();
            }
        });
	}


	public static void openPlayerMenuPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlayerMenuDemo();
            }
        });
	}
	public static void openGameSettingPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameSettingPage();
            }
        });
	}

	public static void openGamePlayPage() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	GamePlayPage ui =new GamePlayPage();
                setCurrentGamePlayPage(ui); // do not change this
            }
        });
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			// for now, we are just creating an empty BTMS
			block223 = Block223Persistence.load();
		}
 		return block223;
	}

	public static UserRole getCurrentUserRole() {
		return currentUserRole;

	}

	public static boolean setCurrentUserRole(UserRole aUserRole) {
		boolean wasSet = false;
	    currentUserRole = aUserRole;
	    wasSet = true;
	    return wasSet;
	}

	public static Game getCurrentGame() {
		return currentGame;

	}

	public static boolean setCurrentGame(Game aGame) {
		boolean wasSet = false;
	    currentGame = aGame;
	    wasSet = true;
	    return wasSet;
	}

	public static boolean setCurrentPlayableGame(PlayedGame aPlayedGame) {
		boolean wasSet = false;
	    currentPlayableGame = aPlayedGame;
	    wasSet = true;
	    return wasSet;
	}
	public static PlayedGame getCurrentPlayableGame() {
		return currentPlayableGame;

	}

	public static Block223 resetBlock223() {

		block223 = Block223Persistence.load();
		setCurrentPlayableGame(null);
		setCurrentGame(null);
		setCurrentGamePlayPage(null);
		return block223;
	}

	public static Block223PlayModeInterface getCurrentGamePlayPage() {
		return currentGamePlayPage;
	}
	public static void setCurrentGamePlayPage(Block223PlayModeInterface ui) {
		currentGamePlayPage = ui;
	}

}
