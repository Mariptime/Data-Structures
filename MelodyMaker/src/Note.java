public class Note {
   private Pitch note;
   private double duration;
   private int octave;
   private Accidental accidental;
   private boolean repeat;
   
   //       and repeat settings.
   public Note(double duration, Pitch note, int octave, Accidental accidental, boolean repeat) {
      if(duration <= 0 || octave < 0 || octave > 10) {
         throw new IllegalArgumentException();
      }
      this.note = note;
      this.duration = duration;
      this.octave = octave;
      this.accidental = accidental;
      this.repeat = repeat;
   }
   
   public Note(double duration, Pitch note, boolean repeat) {
      this(duration, note, 0, Accidental.NATURAL, repeat);
   }
   
   public double getDuration() {
      return duration;
   }
   
   public Accidental getAccidental() {
      return accidental;
   }
   
   public int getOctave() {
      return octave;
   }
   
   public Pitch getPitch() {
      return note;
   }
   
   public void setDuration(double d) {
      if(d <= 0) {
         throw new IllegalArgumentException();    
      }
      duration = d;
   }
   
   public void setAccidental(Accidental a) {
      accidental = a;
   }
   
   // sets the octave of the note to be the passed in octave
   public void setOctave(int octave) {
      if(octave < 0 || octave > 10) {
         throw new IllegalArgumentException();    
      }
      this.octave = octave;
   }
   
   public void setPitch(Pitch pitch) {
      note = pitch;
   }
   
   public void setRepeat(boolean repeat) {
      this.repeat = repeat;
   }
   
   public boolean isRepeat() {
      return repeat;
   }
   
   public void play() {
      StdAudio.play(duration, note, octave, accidental);
   }
   
   //       "<duration> <pitch> <octave> <accidental> <repeat>"
   //       For example "2.3 A 4 SHARP true" and "1.5 R true".
   public String toString() {
      if(note.equals(Pitch.R)) {
         return duration + " " + note + " " + repeat;
      } else {
         return duration + " " + note + " " + octave + " " + accidental + " " + repeat;
      }
   }
}