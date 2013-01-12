#include "Particle.hpp"

using namespace std;

string dtostr(double num) {
  stringstream s;
  s << num;
  return s.str();
} // end string dtostr

double rms(vector<double> v){
  int s=v.size();
  double sum=0,mean=0;
  for(int i=0;i<s;i++){
    mean+=v.at(i);
  }
  mean/=(double) s;
  for(int j=0;j<s;j++){
    sum+=pow(v.at(j)-mean,2);
  }
  return sqrt(sum/(double) s);
} // end double rms

void c_mc (double eng_init, double meas, int &num_c, int &num_n) {

  //sets up random number generator
  timeval t_seed;
  unsigned long seed_key[2];
  gettimeofday(&t_seed,NULL);
  seed_key[0]=t_seed.tv_sec;
  seed_key[1]=t_seed.tv_usec;
  rnd.initArray(seed_key,2);//initializes random number gen with seed

  bool cont = true;
  int p_num=1,prop_res=-1;
  int samp_num=0,samp_num_n=0;
  double temp_e=0,temp_rdm=0;
  vector<Particle*> p(0);

  p.push_back(NULL);
  p.at(0) = new Particle(0,eng_init,0.0,meas,-1);
  while (cont) {
    for(int i=0;i<p_num;i++) {
      if(p.at(i)->GetEng()>0){
	temp_e=p.at(i)->GetEng();
	prop_res=p.at(i)->Prop();
	if(prop_res==0){
	  p.push_back(NULL);
	  p.back()=new Particle(0,temp_e-p.at(i)->GetEng(),
					p.at(i)->GetPos(),meas,i);
	}//brem

	if (prop_res==2){
	  temp_rdm=rnd.genRealOpen();
	  p.push_back(NULL);
	  p.back()=new Particle(1,temp_rdm*temp_e,
					p.at(i)->GetPos(),meas,i);
	  p.push_back(NULL);
	  p.back()=new Particle(1,(1-temp_rdm)*temp_e,
					p.at(i)->GetPos(),meas,i);
	}// pair
	
	if (prop_res==3){
	  
	  temp_rdm=rnd.genRealOpen();
	  p.push_back(NULL);
	  p.back()=new Particle(1,temp_e,p.at(i)->GetPos(),meas,i);
	    
	}// compton
      }
    }// end for i
    p_num=p.size();
    //test to see if shower has resolved
    for(int j=0;j<p_num;j++) {
      cont=false;
      if(!(p.at(j)->GetEng()==0) && !(p.at(j)->GetPos()==28.0)) {
	cont=true;
      }
    }// end shower resolved test
  }// end cont

  for (int l=0;l<p_num;l++) {
    if(p.at(l)->GetSamp1E()>0.0 && p.at(l)->GetType()==0) {
      samp_num_n++;
    }
    if(p.at(l)->GetSamp1E()>0.0 && p.at(l)->GetType()==1) {
	samp_num++;
    }
    delete p.at(l);
  }// end for l

  num_c=samp_num;
  num_n=samp_num_n;

  p.clear(); //cleans up vector
  
}// end c_mc

int main (int argc, char *argv[]) {

  if ( argc != 4 ){
    cout << "Wrong number of input parameters" << argc << endl;
    return 1;
  }// input par error check

  int num_runs=atoi(argv[1]);
  double eng_init=atof(argv[2]);
  double_t meas=atof(argv[3]);

  int samp_val_c[num_runs],samp_val_n[num_runs];
  double sum_c=0,stdev_c=0,sum_n=0,stdev_n=0;
  vector<double> val_dc(0);
  vector<double> val_dn(0);

  for(int i=0;i<num_runs;i++) {
    c_mc(eng_init,meas,samp_val_c[i],samp_val_n[i]);
    val_dc.push_back((double) samp_val_c[i]);
    val_dn.push_back((double) samp_val_n[i]);
    sum_c+=(double) samp_val_c[i];
    sum_n+=(double) samp_val_n[i];
  }

  stdev_c=rms(val_dc);
  stdev_n=rms(val_dn);

  cout << "Average Number of Neutral Particles at " << meas
       << " Rad Lengths for " << num_runs << " Runs:" << endl;
  cout << sum_n/((double) num_runs) << " Error: "
       << stdev_n/sqrt((double) num_runs) <<endl;
  cout << "Average Number of Charged Particles at " << meas
       << " Rad Lengths for " << num_runs << " Runs:" << endl;
  cout << sum_c/((double) num_runs) << " Error: "
       << stdev_c/sqrt((double) num_runs) <<endl;

  return 0;

}// end int main
