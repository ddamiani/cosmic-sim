#include "Particle.hpp"

using namespace std;
using boost::mt19937;
using boost::uniform_01;

string dtostr(double num) {
  stringstream s;
  s << num;
  return s.str();
}// end dtostr

double genRealOpen(mt19937 & gen) {
  uniform_01<mt19937&> dist(gen);
  return dist();
}// end genRealOpen

int main (int argc, char *argv[]) {

  if ( argc != 4 ){
    cout << "Wrong number of input parameters" << argc << endl;
    return 1;
  }// input par error check


  timeval t_seed;
  gettimeofday(&t_seed,NULL);
  mt19937 gen(t_seed.tv_usec);//initializes random number gen with seed
  cout << genRealOpen(gen) << "  " << genRealOpen(gen) << endl;
  double eng_init = atof(argv[1]);
  double meas = atof(argv[2]);
  bool no_dead = false;

  if(atoi(argv[3])==1)
    no_dead=true;

  bool cont = true;
  int p_num=1,prop_res=-1;
  int step_num=0,samp_num=0,samp_num_n=0;
  double temp_e=0,temp_rdm=0;
  vector<Particle*> p(0);

  p.push_back(NULL);
  p.at(0) = new Particle(gen,0,eng_init,0.0,meas,-1);
  while (cont) {
    for(int i=0;i<p_num;i++) {
      if(p.at(i)->GetEng()>0){
	temp_e=p.at(i)->GetEng();
	prop_res=p.at(i)->Prop();
	if(prop_res==0){
	  p.push_back(NULL);
	  p.back()=new Particle(gen,0,temp_e-p.at(i)->GetEng(),
                                p.at(i)->GetPos(),meas,i);
	}//brem

	if (prop_res==2){
	  temp_rdm=genRealOpen(gen);
	  p.push_back(NULL);
	  p.back()=new Particle(gen,1,temp_rdm*temp_e,
                                p.at(i)->GetPos(),meas,i);
	  p.push_back(NULL);
	  p.back()=new Particle(gen,1,(1-temp_rdm)*temp_e,
                                p.at(i)->GetPos(),meas,i);
	}// pair
	
	if (prop_res==3){
	  
	  temp_rdm=genRealOpen(gen);
	  p.push_back(NULL);
	  p.back()=new Particle(gen,1,temp_e,p.at(i)->GetPos(),meas,i);
	    
	}// compton
      }
    }// end for i
    p_num=p.size();
    cout << "Step " << step_num << endl;
    step_num++;
    for(int k=0; k<p_num;k++) {
      if(no_dead) {
	if(p.at(k)->GetEng()>0.0) {
	  cout << k << " "<< p.at(k)->GetType() << " " << p.at(k)->GetEng()
	       << " " << p.at(k)->GetPos()<< " " << p.at(k)->GetParent()
	       << endl;
	}
      }else{
	cout << k << " "<< p.at(k)->GetType() << " " << p.at(k)->GetEng()
	     << " " << p.at(k)->GetPos()<< " " << p.at(k)->GetParent() << endl;
      }
    }// end for k
    //test to see if shower has resolved
    for(int j=0;j<p_num;j++) {
      cont=false;
      if(!(p.at(j)->GetEng()==0) && !(p.at(j)->GetPos()==28.0)) {
	cont=true;
      }
    }// end shower resolved test
  }// end cont

  cout << "Sample Point at " << meas << " Rad Lengths" << endl;
  for (int l=0;l<p_num;l++) {
    if(p.at(l)->GetSamp1E()>0.0) {
      cout << l << " "<< p.at(l)->GetType() << " " << p.at(l)->GetSamp1E()
	   << " " << meas << " " << p.at(l)->GetParent() << endl;
      if(p.at(l)->GetType()==0){
	samp_num_n++;
      }else {
	samp_num++;
      }
    }
    delete p.at(l);
  }// end for l
  cout << "Number of Neutral Particles at Sample Point: " << samp_num_n << endl;
  cout << "Number of Charged Particles at Sample Point: " << samp_num << endl;

  p.clear(); //cleans up vector

  return 0;
  
}// end int main
